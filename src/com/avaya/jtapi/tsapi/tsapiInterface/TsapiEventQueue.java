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

	private static int DEFAULT_TIMEOUT = 180000;

	public TsapiEventQueue(TsapiEventHandler _realHandler, String _debugID) {
		super("DistributeCSTAEvent");
		debugID = _debugID;
		fifo = new Vector<CSTAEvent>();
		realHandler = _realHandler;
		start();
	}

	public void close() {
		keepRunning = false;
		synchronized (this) {
			super.notify();
		}
	}

	// ERROR //
	private CSTAEvent get() {
		CSTAEvent event;
		synchronized (this) {
			int size = 0;

			while ((keepRunning == true) && ((size = fifo.size()) == 0)) {
				try {
					super.wait(DEFAULT_TIMEOUT);
				} catch (InterruptedException e) {
				}
			}
			if (!keepRunning) {
				return null;
			}

			event = fifo.elementAt(size - 1);
			fifo.removeElementAt(size - 1);
		}

		// if (TSProvider.debugLevelIsActive(TSProvider.DEBUGLEVEL_HANDLER)) {
		// Tsapi.log
		// .println("Getting event " + event + " for " + this.debugID);
		// }

		return event;
	}

	public void handleEvent(CSTAEvent event) {
		put(event);
	}

	private void put(CSTAEvent event) {
		int prevsize;
		int debugMaxsize;
		synchronized (this) {
			prevsize = fifo.size();

			fifo.insertElementAt(event, 0);

			event.setQueuedTimeStamp(System.currentTimeMillis());

			if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
				int evType = event.getEventHeader().getEventClass();

				if ((evType == 1) || (evType == 4)) {
					log
							.debug("Updating statistics collector with info for EVENTS/SEC.");
					PerfStatisticsCollector.updateEventCount();
				}
			}

			if (prevsize == 0) {
				super.notify();
			}

			++prevsize;
			if (prevsize > maxsize) {
				maxsize = prevsize;
			}
			debugMaxsize = maxsize;
		}

		log.info("Putting event " + event + ". EVENT Q SIZE = " + prevsize
				+ " MAX Q SIZE = " + debugMaxsize + " for " + debugID);

		if ((fifo.size() == 100)
				|| ((fifo.size() > 100) && (fifo.size() % 300 == 0))) {
			log.info("Doing Thread dumps");
			ThreadDump threadDump = new ThreadDump();
			threadDump.start();
		}

		if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
			log.debug("Incrementing statistics collector with queue length");
			PerfStatisticsCollector.updateQueueLength(queueLength
					.incrementAndGet());
		}

		if (fifo.size() == 1000) {
			ThreadDump threadDump = new ThreadDump();
			threadDump.start();
			throw new TsapiPlatformException(4, 0,
					"Event queue size has reached the threshold of 1000. Shutting down.");
		}
	}

	@Override
	public void run() {
		while (keepRunning) {
			CSTAEvent event = get();

			if (event != null) {
				realHandler.handleEvent(event);
			}
		}
	}

	public void setUnsolicitedHandler(TsapiUnsolicitedHandler handler) {
		realHandler.setUnsolicitedHandler(handler);
	}
}

