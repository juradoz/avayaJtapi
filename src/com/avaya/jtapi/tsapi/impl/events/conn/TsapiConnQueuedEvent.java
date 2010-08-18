package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnQueuedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnQueuedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnQueuedEv, ITsapiCallInfo {
	private int numberQueued = 0;

	public TsapiConnQueuedEvent(final ConnEventParams params,
			final int numberQueued) {
		super(params);
		this.numberQueued = numberQueued;
	}

	public final int getID() {
		return 212;
	}

	public final int getNumberInQueue() {
		return numberQueued;
	}
}
