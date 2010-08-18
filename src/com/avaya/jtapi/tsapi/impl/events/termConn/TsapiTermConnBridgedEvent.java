package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnBridgedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiTermConnBridgedEvent extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnBridgedEv, ITsapiCallInfo {
	public TsapiTermConnBridgedEvent(TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 214;
	}
}

