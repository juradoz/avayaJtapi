package com.avaya.jtapi.tsapi.tsapiInterface;

import org.apache.log4j.Logger;

class TsapiHeartbeatStatus {
	private static Logger log = Logger.getLogger(TsapiHeartbeatStatus.class);
	private static final short HEARTBEAT_INTERVAL_DEFAULT = 20;
	private boolean enabled;
	private short interval;
	private final TsapiHeartbeatTimer timer;

	TsapiHeartbeatStatus() {
		enabled = false;
		interval = TsapiHeartbeatStatus.HEARTBEAT_INTERVAL_DEFAULT;
		timer = new TsapiHeartbeatTimer(getTimeout());
	}

	synchronized void disableHeartbeat() {
		enabled = false;
		timer.cancel();
	}

	synchronized void enableHeartbeat() {
		if (enabled)
			return;
		TsapiHeartbeatStatus.log
				.info("Enabling the TSAPI heartbeat with a heartbeat interval of "
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
		if (!enabled)
			return;
		timer.reset();
	}

	synchronized void setHeartbeatInterval(final short heartbeatInterval)
			throws IllegalArgumentException {
		if (heartbeatInterval < 0)
			throw new IllegalArgumentException(
					"Heartbeat interval must be non-negative.");

		interval = heartbeatInterval;
		if (!enabled)
			return;
		timer.reset(getTimeout());
	}

	synchronized void setHeartbeatTimeoutListener(
			final ITsapiHeartbeatTimeoutListener listener) {
		timer.setHeartbeatTimeoutListener(listener);
	}
}
