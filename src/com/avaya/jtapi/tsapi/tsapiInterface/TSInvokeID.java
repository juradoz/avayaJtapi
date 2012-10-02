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
		this.value = _value;
		this.handler = _handler;
		this.debugID = _debugID;
		this.conf = null;
	}

	int getValue() {
		return this.value;
	}

	ConfHandler getConfHandler() {
		return this.handler;
	}

	public long getServiceRequestTurnaroundTime() {
		return this.serviceRequestTurnaroundTime;
	}

	public void setServiceRequestTurnaroundTime(
			long serviceRequestTurnaroundTime) {
		this.serviceRequestTurnaroundTime = serviceRequestTurnaroundTime;
	}

	synchronized void setConf(CSTAEvent _conf) {
		try {
			log.info("Handling INVOKE ID " + this.value + " for "
					+ this.debugID);
			this.conf = _conf;
			if (this.handler != null)
				this.handler.handleConf(this.conf);
			log.info("DONE handling INVOKE ID " + this.value + " for "
					+ this.debugID);
		} finally {
			setServiceRequestTurnaroundTime(System.currentTimeMillis()
					- getServiceRequestTurnaroundTime());
			if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
				PerfStatisticsCollector
						.updateServiceRequestTurnaroundTime(getServiceRequestTurnaroundTime());
			}
			notify();
		}
	}

	synchronized CSTAEvent waitForConf(int timeout) {
		if (this.conf == null) {
			try {
				wait(timeout);
			} catch (InterruptedException e) {
			}
		}
		return this.conf;
	}
}