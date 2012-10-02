package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;

public class TsapiEventQueue extends Thread implements TsapiEventHandler {
	private static Logger log = Logger.getLogger(TsapiEventQueue.class);

	private static AtomicInteger queueLength = new AtomicInteger();
	private String debugID;
	private Vector<CSTAEvent> fifo;
	private int maxsize = 0;
	private TsapiEventHandler realHandler;
	private boolean keepRunning = true;
	private long timeWhenLastDumped = 0L;

	private static int DEFAULT_TIMEOUT = 180000;

	public TsapiEventQueue(TsapiEventHandler _realHandler, String _debugID) {
		super("DistributeCSTAEvent");
		this.debugID = _debugID;
		this.fifo = new Vector<CSTAEvent>();
		this.realHandler = _realHandler;
		start();
	}

	public void handleEvent(CSTAEvent event) {
		put(event);
	}

	public void run() {
		while (this.keepRunning) {
			CSTAEvent event = get();

			if (event != null)
				this.realHandler.handleEvent(event);
		}
	}

	public void close() {
		this.keepRunning = false;
		synchronized (this) {
			notify();
		}
	}

	public void setUnsolicitedHandler(TsapiUnsolicitedHandler handler) {
		this.realHandler.setUnsolicitedHandler(handler);
	}

	private void put(CSTAEvent event) {
		int prevsize;
		int debugMaxsize;
		synchronized (this) {
			prevsize = this.fifo.size();

			this.fifo.insertElementAt(event, 0);

			event.setQueuedTimeStamp(System.currentTimeMillis());

			if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
				int evType = event.getEventHeader().getEventClass();

				if ((evType == 1) || (evType == 4)) {
					log.debug("Updating statistics collector with info for EVENTS/SEC.");
					PerfStatisticsCollector.updateEventCount();
				}
			}

			if (prevsize == 0) {
				notify();
			}

			prevsize++;
			if (prevsize > this.maxsize) {
				this.maxsize = prevsize;
			}
			debugMaxsize = this.maxsize;
		}

		log.info("Putting event " + event + ". EVENT Q SIZE = " + prevsize
				+ " MAX Q SIZE = " + debugMaxsize + " for " + this.debugID);

		if ((this.fifo.size() == 100)
				|| ((this.fifo.size() > 100) && (this.fifo.size() % 300 == 0))) {
			if (System.currentTimeMillis() - this.timeWhenLastDumped > 10000L) {
				this.timeWhenLastDumped = System.currentTimeMillis();
				log.info("Doing Thread dumps");
				ThreadDump threadDump = new ThreadDump();
				threadDump.start();
			}
		}

		if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
			log.debug("Incrementing statistics collector with queue length");
			PerfStatisticsCollector.updateQueueLength(queueLength
					.incrementAndGet());
		}

		if (this.fifo.size() == 1000) {
			ThreadDump threadDump = new ThreadDump();
			threadDump.start();
			throw new TsapiPlatformException(4, 0,
					"Event queue size has reached the threshold of 1000. Shutting down.");
		}
	}

	private CSTAEvent get() {
		CSTAEvent event;
		synchronized (this) {
			int size = 0;

			while ((this.keepRunning == true)
					&& ((size = this.fifo.size()) == 0))
				try {
					wait(DEFAULT_TIMEOUT);
				} catch (InterruptedException e) {
				}
			if (!this.keepRunning) {
				return null;
			}

			event = (CSTAEvent) this.fifo.elementAt(size - 1);
			this.fifo.removeElementAt(size - 1);
		}

		log.info("Getting event " + event + " for " + this.debugID);

		if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
			log.debug("Updating statistics collector with MESSAGE LATENCY for this iteration:");
			PerfStatisticsCollector.updateMessageLatency(System
					.currentTimeMillis() - event.getQueuedTimeStamp());
		}

		if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
			log.debug("Decrementing statistics collector with queue length");
			PerfStatisticsCollector.updateQueueLength(queueLength
					.decrementAndGet());
		}
		return event;
	}
}