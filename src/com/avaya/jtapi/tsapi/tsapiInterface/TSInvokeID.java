package com.avaya.jtapi.tsapi.tsapiInterface;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;

final class TSInvokeID {
	private static Logger log = Logger.getLogger(TSInvokeID.class);
	int value;
	CSTAEvent conf;
	ConfHandler handler;
	String debugID;
	long serviceRequestTurnaroundTime;

	TSInvokeID(int _value, ConfHandler _handler, String _debugID) {
		value = _value;
		handler = _handler;
		debugID = _debugID;
		conf = null;
	}

	ConfHandler getConfHandler() {
		return handler;
	}

	public long getServiceRequestTurnaroundTime() {
		return serviceRequestTurnaroundTime;
	}

	int getValue() {
		return value;
	}

	synchronized void setConf(CSTAEvent _conf) {
		try {
			log.info("Handling INVOKE ID " + value + " for " + debugID);
			conf = _conf;
			if (handler != null) {
				handler.handleConf(conf);
			}
			log.info("DONE handling INVOKE ID " + value + " for " + debugID);
		} finally {
			setServiceRequestTurnaroundTime(System.currentTimeMillis()
					- getServiceRequestTurnaroundTime());
			if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
				PerfStatisticsCollector
						.updateServiceRequestTurnaroundTime(getServiceRequestTurnaroundTime());
			}
			super.notify();
		}
	}

	public void setServiceRequestTurnaroundTime(
			long serviceRequestTurnaroundTime) {
		this.serviceRequestTurnaroundTime = serviceRequestTurnaroundTime;
	}

	synchronized CSTAEvent waitForConf(int timeout) {
		if (conf == null) {
			try {
				super.wait(timeout);
			} catch (InterruptedException e) {
			}
		}
		return conf;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TSInvokeID JD-Core Version: 0.5.4
 */