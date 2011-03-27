package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnInUseEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiTermConnInUseEvent extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnInUseEv, ITsapiCallInfo {
	public TsapiTermConnInUseEvent(final TermConnEventParams params) {
		super(params);
	}

	@Override
	public final int getID() {
		return 217;
	}
}
