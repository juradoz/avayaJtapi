package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnDroppedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiTermConnDroppedEventCC extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnDroppedEv, ITsapiCallInfo {
	public TsapiTermConnDroppedEventCC(final TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 215;
	}
}
