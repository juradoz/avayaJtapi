package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnRingingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiTermConnRingingEventCC extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnRingingEv, ITsapiCallInfo {
	public TsapiTermConnRingingEventCC(final TermConnEventParams params) {
		super(params);
	}

	@Override
	public final int getID() {
		return 218;
	}
}
