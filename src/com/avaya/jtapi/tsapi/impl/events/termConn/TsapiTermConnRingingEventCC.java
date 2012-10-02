package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlTermConnRingingEv;

@SuppressWarnings("deprecation")
public class TsapiTermConnRingingEventCC extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnRingingEv, ITsapiCallInfo {
	public final int getID() {
		return 218;
	}

	public TsapiTermConnRingingEventCC(TermConnEventParams params) {
		super(params);
	}
}