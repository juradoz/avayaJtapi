package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlTermConnDroppedEv;

@SuppressWarnings("deprecation")
public class TsapiTermConnDroppedEventCC extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnDroppedEv, ITsapiCallInfo {
	public final int getID() {
		return 215;
	}

	public TsapiTermConnDroppedEventCC(TermConnEventParams params) {
		super(params);
	}
}