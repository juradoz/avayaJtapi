package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.Timer;
import java.util.TimerTask;

class TsapiHeartbeatTimer {
	class TsapiHeartbeatTimerTask extends TimerTask {
		TsapiHeartbeatTimerTask() {
		}

		@Override
		public void run() {
			if (listener == null)
				return;
			listener.heartbeatTimeout();
		}
	}

	private int delay;
	private Timer timer;

	private ITsapiHeartbeatTimeoutListener listener;

	TsapiHeartbeatTimer(final int delay) {
		this.delay = delay;
		timer = null;
	}

	void cancel() {
		cancelCurrentTimer();
	}

	private void cancelCurrentTimer() {
		if (timer == null)
			return;
		timer.cancel();
		timer = null;
	}

	int getDelay() {
		return delay;
	}

	void reset() throws IllegalArgumentException, IllegalStateException {
		cancelCurrentTimer();
		scheduleNewTimer();
	}

	void reset(final int delay) throws IllegalArgumentException,
			IllegalStateException {
		this.delay = delay;
		cancelCurrentTimer();
		scheduleNewTimer();
	}

	private void scheduleNewTimer() throws IllegalArgumentException,
			IllegalStateException {
		timer = new Timer();
		timer.schedule(new TsapiHeartbeatTimerTask(), delay * 1000);
	}

	void setHeartbeatTimeoutListener(
			final ITsapiHeartbeatTimeoutListener listener) {
		this.listener = listener;
	}
}
