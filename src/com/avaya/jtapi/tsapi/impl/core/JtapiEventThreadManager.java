package com.avaya.jtapi.tsapi.impl.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;

public class JtapiEventThreadManager {
	private static Logger logger = Logger
			.getLogger(JtapiEventThreadManager.class);
	private static ExecutorService threadPoolExecutor;
	private static BlockingQueue<Runnable> fifoBuffer;

	public static void drainThreads() {
		if (JtapiEventThreadManager.threadPoolExecutor != null)
			JtapiEventThreadManager.threadPoolExecutor.shutdown();
		JtapiEventThreadManager.threadPoolExecutor = null;
	}

	public static void execute(final TsapiMonitor _obs) {
		if (JtapiEventThreadManager.threadPoolExecutor == null)
			JtapiEventThreadManager.logger
					.error("ThreadPoolExecutor is not initialized. This can happen when the only provider is SHUTDOWN.");
		else {
			if (((ThreadPoolExecutor) JtapiEventThreadManager.threadPoolExecutor)
					.getCorePoolSize() != Tsapi.getMaxThreadPoolSize()) {
				((ThreadPoolExecutor) JtapiEventThreadManager.threadPoolExecutor)
						.setCorePoolSize(Tsapi.getMaxThreadPoolSize());
				((ThreadPoolExecutor) JtapiEventThreadManager.threadPoolExecutor)
						.setMaximumPoolSize(Tsapi.getMaxThreadPoolSize());
			}
			JtapiEventThreadManager.threadPoolExecutor
					.execute(new JtapiEventDeliveryThread(_obs, System
							.currentTimeMillis()));
		}
	}

	public static int getQueueSize() {
		if (JtapiEventThreadManager.fifoBuffer == null)
			JtapiEventThreadManager.fifoBuffer = new LinkedBlockingQueue<Runnable>();
		int toReturn;
		synchronized (JtapiEventThreadManager.fifoBuffer) {
			toReturn = JtapiEventThreadManager.fifoBuffer.size();
		}

		return toReturn;
	}

	public static synchronized void initialize() {
		if (JtapiEventThreadManager.threadPoolExecutor == null) {
			final int defaultValue = Integer.parseInt("20");
			JtapiEventThreadManager.fifoBuffer = new LinkedBlockingQueue<Runnable>();
			JtapiEventThreadManager.threadPoolExecutor = new ThreadPoolExecutor(
					defaultValue, defaultValue, 200L, TimeUnit.MILLISECONDS,
					JtapiEventThreadManager.fifoBuffer,
					new JtapiEventThreadRejectionHandler());
		}
	}
}
