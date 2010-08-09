package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnBridgedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiTermConnBridgedEvent extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnBridgedEv, ITsapiCallInfo {
	public TsapiTermConnBridgedEvent(TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 214;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnBridgedEvent JD-Core
 * Version: 0.5.4
 */