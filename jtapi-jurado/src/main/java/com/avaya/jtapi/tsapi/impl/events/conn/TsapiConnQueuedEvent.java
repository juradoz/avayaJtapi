package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnQueuedEv;

@SuppressWarnings("deprecation")
public class TsapiConnQueuedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnQueuedEv, ITsapiCallInfo {
	private int numberQueued = 0;

	public final int getID() {
		return 212;
	}

	public final int getNumberInQueue() {
		return this.numberQueued;
	}

	public TsapiConnQueuedEvent(ConnEventParams params, int numberQueued) {
		super(params);
		this.numberQueued = numberQueued;
	}
}