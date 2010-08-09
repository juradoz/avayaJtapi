package com.avaya.jtapi.tsapi.tsapiInterface;

import org.apache.log4j.Logger;

class TsapiHeartbeatStatus {
	private static Logger log = Logger.getLogger(TsapiHeartbeatStatus.class);
	private static final short HEARTBEAT_INTERVAL_DEFAULT = 20;
	private boolean enabled;
	private short interval;
	private TsapiHeartbeatTimer timer;

	TsapiHeartbeatStatus() {
		enabled = false;
		interval = 20;
		timer = new TsapiHeartbeatTimer(getTimeout());
	}

	synchronized void disableHeartbeat() {
		enabled = false;
		timer.cancel();
	}

	synchronized void enableHeartbeat() {
		if (enabled) {
			return;
		}
		log.info("Enabling the TSAPI heartbeat with a heartbeat interval of "
				+ interval + " seconds.");

		timer.reset(getTimeout());
		enabled = true;
	}

	short getHeartbeatInterval() {
		return interval;
	}

	private int getTimeout() {
		return 2 * interval;
	}

	synchronized boolean heartbeatIsEnabled() {
		return enabled;
	}

	synchronized void receivedEvent() {
		if (!enabled) {
			return;
		}
		timer.reset();
	}

	synchronized void setHeartbeatInterval(short heartbeatInterval)
			throws IllegalArgumentException {
		if (heartbeatInterval < 0) {
			throw new IllegalArgumentException(
					"Heartbeat interval must be non-negative.");
		}

		interval = heartbeatInterval;
		if (!enabled) {
			return;
		}
		timer.reset(getTimeout());
	}

	synchronized void setHeartbeatTimeoutListener(
			ITsapiHeartbeatTimeoutListener listener) {
		timer.setHeartbeatTimeoutListener(listener);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TsapiHeartbeatStatus JD-Core Version:
 * 0.5.4
 */