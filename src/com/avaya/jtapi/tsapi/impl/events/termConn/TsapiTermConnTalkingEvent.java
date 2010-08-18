package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnTalkingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiTermConnTalkingEvent extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnTalkingEv, ITsapiCallInfo {
	public TsapiTermConnTalkingEvent(final TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 219;
	}
}
