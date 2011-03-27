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
	private final String debugID;
	private final Vector<CSTAEvent> fifo;
	private int maxsize = 0;
	private final TsapiEventHandler realHandler;
	private boolean keepRunning = true;

	private static int DEFAULT_TIMEOUT = 180000;

	public TsapiEventQueue(final TsapiEventHandler _realHandler,
			final String _debugID) {
		super("DistributeCSTAEvent");
		debugID = _debugID;
		fifo = new Vector<CSTAEvent>();
		realHandler = _realHandler;
		start();
	}

	@Override
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

			while (keepRunning == true && (size = fifo.size()) == 0)
				try {
					super.wait(TsapiEventQueue.DEFAULT_TIMEOUT);
				} catch (final InterruptedException e) {
				}
			if (!keepRunning)
				return null;

			event = fifo.elementAt(size - 1);
			fifo.removeElementAt(size - 1);
		}

		// if (TSProvider.debugLevelIsActive(TSProvider.DEBUGLEVEL_HANDLER)) {
		// Tsapi.log
		// .println("Getting event " + event + " for " + this.debugID);
		// }

		return event;
	}

	@Override
	public void handleEvent(final CSTAEvent event) {
		put(event);
	}

	private void put(final CSTAEvent event) {
		int prevsize;
		int debugMaxsize;
		synchronized (this) {
			prevsize = fifo.size();

			fifo.insertElementAt(event, 0);

			event.setQueuedTimeStamp(System.currentTimeMillis());

			if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
				final int evType = event.getEventHeader().getEventClass();

				if (evType == 1 || evType == 4) {
					TsapiEventQueue.log
							.debug("Updating statistics collector with info for EVENTS/SEC.");
					PerfStatisticsCollector.updateEventCount();
				}
			}

			if (prevsize == 0)
				super.notify();

			++prevsize;
			if (prevsize > maxsize)
				maxsize = prevsize;
			debugMaxsize = maxsize;
		}

		TsapiEventQueue.log.info("Putting event " + event + ". EVENT Q SIZE = "
				+ prevsize + " MAX Q SIZE = " + debugMaxsize + " for "
				+ debugID);

		if (fifo.size() == 100 || fifo.size() > 100 && fifo.size() % 300 == 0) {
			TsapiEventQueue.log.info("Doing Thread dumps");
			final ThreadDump threadDump = new ThreadDump();
			threadDump.start();
		}

		if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
			TsapiEventQueue.log
					.debug("Incrementing statistics collector with queue length");
			PerfStatisticsCollector
					.updateQueueLength(TsapiEventQueue.queueLength
							.incrementAndGet());
		}

		if (fifo.size() == 1000) {
			final ThreadDump threadDump = new ThreadDump();
			threadDump.start();
			throw new TsapiPlatformException(4, 0,
					"Event queue size has reached the threshold of 1000. Shutting down.");
		}
	}

	@Override
	public void run() {
		while (keepRunning) {
			final CSTAEvent event = get();

			if (event != null)
				realHandler.handleEvent(event);
		}
	}

	@Override
	public void setUnsolicitedHandler(final TsapiUnsolicitedHandler handler) {
		realHandler.setUnsolicitedHandler(handler);
	}
}
