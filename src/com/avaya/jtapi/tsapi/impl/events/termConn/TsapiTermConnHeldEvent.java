package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnHeldEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiTermConnHeldEvent extends TsapiCallCtlTermConnEvent implements
		CallCtlTermConnHeldEv, ITsapiCallInfo {
	public TsapiTermConnHeldEvent(final TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 216;
	}
}
