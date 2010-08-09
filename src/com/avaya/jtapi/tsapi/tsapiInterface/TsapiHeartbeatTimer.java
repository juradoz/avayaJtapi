package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.Timer;
import java.util.TimerTask;

class TsapiHeartbeatTimer {
	class TsapiHeartbeatTimerTask extends TimerTask {
		TsapiHeartbeatTimerTask() {
		}

		@Override
		public void run() {
			if (listener == null) {
				return;
			}
			listener.heartbeatTimeout();
		}
	}

	private int delay;
	private Timer timer;

	private ITsapiHeartbeatTimeoutListener listener;

	TsapiHeartbeatTimer(int delay) {
		this.delay = delay;
		timer = null;
	}

	void cancel() {
		cancelCurrentTimer();
	}

	private void cancelCurrentTimer() {
		if (timer == null) {
			return;
		}
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

	void reset(int delay) throws IllegalArgumentException,
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

	void setHeartbeatTimeoutListener(ITsapiHeartbeatTimeoutListener listener) {
		this.listener = listener;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TsapiHeartbeatTimer JD-Core Version:
 * 0.5.4
 */