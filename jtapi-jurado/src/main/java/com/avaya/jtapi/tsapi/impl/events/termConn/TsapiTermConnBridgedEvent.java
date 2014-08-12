package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlTermConnBridgedEv;

@SuppressWarnings("deprecation")
public class TsapiTermConnBridgedEvent extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnBridgedEv, ITsapiCallInfo {
	public final int getID() {
		return 214;
	}

	public TsapiTermConnBridgedEvent(TermConnEventParams params) {
		super(params);
	}
}