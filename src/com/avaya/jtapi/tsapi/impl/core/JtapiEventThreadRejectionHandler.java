package com.avaya.jtapi.tsapi.impl.core;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

public class JtapiEventThreadRejectionHandler implements
		RejectedExecutionHandler {
	private static Logger log = Logger
			.getLogger(JtapiEventThreadRejectionHandler.class);

	public void rejectedExecution(final Runnable r,
			final ThreadPoolExecutor executor) {
		JtapiEventThreadRejectionHandler.log
				.info("There are already "
						+ executor.getActiveCount()
						+ " active threads delivering events and . "
						+ executor.getQueue().size()
						+ " requests in queue."
						+ "No free threads were available to assign. Kindly consider setting the maxThreadPoolSize to a higher value");
	}
}
