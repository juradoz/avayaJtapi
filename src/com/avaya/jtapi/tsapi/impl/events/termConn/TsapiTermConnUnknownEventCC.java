package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnUnknownEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiTermConnUnknownEventCC extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnUnknownEv, ITsapiCallInfo {
	public TsapiTermConnUnknownEventCC(final TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 220;
	}
}
