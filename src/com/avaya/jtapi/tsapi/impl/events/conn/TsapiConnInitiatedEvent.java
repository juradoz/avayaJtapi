package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnInitiatedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnInitiatedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnInitiatedEv, ITsapiCallInfo {
	public TsapiConnInitiatedEvent(final ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 208;
	}
}
