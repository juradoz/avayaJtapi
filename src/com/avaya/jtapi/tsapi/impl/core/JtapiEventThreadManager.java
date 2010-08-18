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
		if (threadPoolExecutor != null) {
			threadPoolExecutor.shutdown();
		}
		threadPoolExecutor = null;
	}

	public static void execute(TsapiMonitor _obs) {
		if (threadPoolExecutor == null) {
			logger
					.error("ThreadPoolExecutor is not initialized. This can happen when the only provider is SHUTDOWN.");
		} else {
			if (((ThreadPoolExecutor) threadPoolExecutor).getCorePoolSize() != Tsapi
					.getMaxThreadPoolSize()) {
				((ThreadPoolExecutor) threadPoolExecutor).setCorePoolSize(Tsapi
						.getMaxThreadPoolSize());
				((ThreadPoolExecutor) threadPoolExecutor)
						.setMaximumPoolSize(Tsapi.getMaxThreadPoolSize());
			}
			threadPoolExecutor.execute(new JtapiEventDeliveryThread(_obs,
					System.currentTimeMillis()));
		}
	}

	public static int getQueueSize() {
		if (fifoBuffer == null) {
			fifoBuffer = new LinkedBlockingQueue<Runnable>();
		}
		int toReturn;
		synchronized (fifoBuffer) {
			toReturn = fifoBuffer.size();
		}

		return toReturn;
	}

	public static synchronized void initialize() {
		if (threadPoolExecutor == null) {
			int defaultValue = Integer.parseInt("20");
			fifoBuffer = new LinkedBlockingQueue<Runnable>();
			threadPoolExecutor = new ThreadPoolExecutor(defaultValue,
					defaultValue, 200L, TimeUnit.MILLISECONDS, fifoBuffer,
					new JtapiEventThreadRejectionHandler());
		}
	}
}
