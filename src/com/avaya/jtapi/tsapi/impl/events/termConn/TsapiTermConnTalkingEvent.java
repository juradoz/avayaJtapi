package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnTalkingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiTermConnTalkingEvent extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnTalkingEv, ITsapiCallInfo {
	public TsapiTermConnTalkingEvent(TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 219;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnTalkingEvent JD-Core
 * Version: 0.5.4
 */