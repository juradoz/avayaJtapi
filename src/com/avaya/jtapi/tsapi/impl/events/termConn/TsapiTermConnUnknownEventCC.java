package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnUnknownEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiTermConnUnknownEventCC extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnUnknownEv, ITsapiCallInfo {
	public TsapiTermConnUnknownEventCC(TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 220;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnUnknownEventCC
 * JD-Core Version: 0.5.4
 */