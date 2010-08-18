package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnDisconnectedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnDisconnectedEventCC extends TsapiCallCtlConnEvent
		implements CallCtlConnDisconnectedEv, ITsapiCallInfo {
	public TsapiConnDisconnectedEventCC(final ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 205;
	}
}
