package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiMonitor;
import org.apache.log4j.Logger;

public class JtapiEventDeliveryThread implements Runnable {
	private static Logger log = Logger
			.getLogger(JtapiEventDeliveryThread.class);
	private TsapiMonitor tsapiObserver;
	private long creationTime;

	public JtapiEventDeliveryThread(TsapiMonitor observer, long systemTime) {
		this.tsapiObserver = observer;
		this.creationTime = systemTime;
	}

	public void run() {
		if (System.currentTimeMillis() - this.creationTime >= 5000L)
			log.info("Queue processing is very slow. Consider increasing maxThreadPoolSize property");
		this.tsapiObserver.run();
	}
}