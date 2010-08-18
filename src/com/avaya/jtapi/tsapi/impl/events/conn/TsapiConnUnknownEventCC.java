package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnUnknownEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnUnknownEventCC extends TsapiCallCtlConnEvent implements
		CallCtlConnUnknownEv, ITsapiCallInfo {
	public TsapiConnUnknownEventCC(ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 213;
	}
}

