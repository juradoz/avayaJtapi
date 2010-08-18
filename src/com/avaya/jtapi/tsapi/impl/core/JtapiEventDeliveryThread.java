package com.avaya.jtapi.tsapi.impl.core;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiMonitor;

public class JtapiEventDeliveryThread implements Runnable {
	private static Logger log = Logger
			.getLogger(JtapiEventDeliveryThread.class);
	private final TsapiMonitor tsapiObserver;
	private final long creationTime;

	public JtapiEventDeliveryThread(final TsapiMonitor observer,
			final long systemTime) {
		tsapiObserver = observer;
		creationTime = systemTime;
	}

	public void run() {
		if (System.currentTimeMillis() - creationTime >= 5000L)
			JtapiEventDeliveryThread.log
					.info("Queue processing is very slow. Consider increasing maxThreadPoolSize property");
		tsapiObserver.run();
	}
}
