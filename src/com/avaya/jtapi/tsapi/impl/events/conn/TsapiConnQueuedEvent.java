package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnQueuedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnQueuedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnQueuedEv, ITsapiCallInfo {
	private int numberQueued = 0;

	public TsapiConnQueuedEvent(ConnEventParams params, int numberQueued) {
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnQueuedEvent JD-Core Version:
 * 0.5.4
 */