package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlTermConnTalkingEv;

@SuppressWarnings("deprecation")
public class TsapiTermConnTalkingEvent extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnTalkingEv, ITsapiCallInfo {
	public final int getID() {
		return 219;
	}

	public TsapiTermConnTalkingEvent(TermConnEventParams params) {
		super(params);
	}
}