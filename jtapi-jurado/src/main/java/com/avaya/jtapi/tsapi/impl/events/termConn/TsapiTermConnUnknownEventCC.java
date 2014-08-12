package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlTermConnUnknownEv;

@SuppressWarnings("deprecation")
public class TsapiTermConnUnknownEventCC extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnUnknownEv, ITsapiCallInfo {
	public final int getID() {
		return 220;
	}

	public TsapiTermConnUnknownEventCC(TermConnEventParams params) {
		super(params);
	}
}