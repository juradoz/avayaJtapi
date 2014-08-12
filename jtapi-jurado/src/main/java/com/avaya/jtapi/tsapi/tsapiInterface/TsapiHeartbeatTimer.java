package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.Timer;
import java.util.TimerTask;

class TsapiHeartbeatTimer {
	private int delay;
	private Timer timer;
	private ITsapiHeartbeatTimeoutListener listener;

	TsapiHeartbeatTimer(int delay) {
		this.delay = delay;
		this.timer = null;
	}

	int getDelay() {
		return this.delay;
	}

	void reset() throws IllegalArgumentException, IllegalStateException {
		cancelCurrentTimer();
		scheduleNewTimer();
	}

	void reset(int delay) throws IllegalArgumentException,
			IllegalStateException {
		this.delay = delay;
		cancelCurrentTimer();
		scheduleNewTimer();
	}

	void cancel() {
		cancelCurrentTimer();
	}

	void setHeartbeatTimeoutListener(ITsapiHeartbeatTimeoutListener listener) {
		this.listener = listener;
	}

	private void cancelCurrentTimer() {
		if (this.timer != null) {
			this.timer.cancel();
			this.timer = null;
		}
	}

	private void scheduleNewTimer() throws IllegalArgumentException,
			IllegalStateException {
		this.timer = new Timer();
		this.timer.schedule(new TsapiHeartbeatTimerTask(), this.delay * 1000);
	}

	class TsapiHeartbeatTimerTask extends TimerTask {
		TsapiHeartbeatTimerTask() {
		}

		public void run() {
			if (TsapiHeartbeatTimer.this.listener != null) {
				TsapiHeartbeatTimer.this.listener.heartbeatTimeout();
			}
		}
	}
}