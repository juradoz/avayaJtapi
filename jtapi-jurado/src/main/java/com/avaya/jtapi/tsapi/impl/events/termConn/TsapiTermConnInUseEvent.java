package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlTermConnInUseEv;

@SuppressWarnings("deprecation")
public class TsapiTermConnInUseEvent extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnInUseEv, ITsapiCallInfo {
	public final int getID() {
		return 217;
	}

	public TsapiTermConnInUseEvent(TermConnEventParams params) {
		super(params);
	}
}