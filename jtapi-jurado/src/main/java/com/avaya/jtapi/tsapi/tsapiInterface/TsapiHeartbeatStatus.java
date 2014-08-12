package com.avaya.jtapi.tsapi.tsapiInterface;

import org.apache.log4j.Logger;

class TsapiHeartbeatStatus {
	private static Logger log = Logger.getLogger(TsapiHeartbeatStatus.class);
	static final short HEARTBEAT_INTERVAL_DEFAULT = 20;
	private boolean enabled;
	private short interval;
	private TsapiHeartbeatTimer timer;

	TsapiHeartbeatStatus() {
		this.enabled = false;
		this.interval = 20;
		this.timer = new TsapiHeartbeatTimer(getTimeout());
	}

	private int getTimeout() {
		return 2 * this.interval;
	}

	synchronized void enableHeartbeat() {
		if (!this.enabled) {
			log.info("Enabling the TSAPI heartbeat with a heartbeat interval of "
					+ this.interval + " seconds.");

			this.timer.reset(getTimeout());
			this.enabled = true;
		}
	}

	synchronized void disableHeartbeat() {
		this.enabled = false;
		this.timer.cancel();
	}

	synchronized boolean heartbeatIsEnabled() {
		return this.enabled;
	}

	synchronized void setHeartbeatInterval(short heartbeatInterval)
			throws IllegalArgumentException {
		if (heartbeatInterval < 0) {
			throw new IllegalArgumentException(
					"Heartbeat interval must be non-negative.");
		}

		this.interval = heartbeatInterval;
		if (this.enabled) {
			this.timer.reset(getTimeout());
		}
	}

	short getHeartbeatInterval() {
		return this.interval;
	}

	synchronized void receivedEvent() {
		if (this.enabled) {
			this.timer.reset();
		}
	}

	synchronized void setHeartbeatTimeoutListener(
			ITsapiHeartbeatTimeoutListener listener) {
		this.timer.setHeartbeatTimeoutListener(listener);
	}
}