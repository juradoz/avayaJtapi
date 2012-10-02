package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlTermConnHeldEv;

@SuppressWarnings("deprecation")
public class TsapiTermConnHeldEvent extends TsapiCallCtlTermConnEvent implements
		CallCtlTermConnHeldEv, ITsapiCallInfo {
	public final int getID() {
		return 216;
	}

	public TsapiTermConnHeldEvent(TermConnEventParams params) {
		super(params);
	}
}