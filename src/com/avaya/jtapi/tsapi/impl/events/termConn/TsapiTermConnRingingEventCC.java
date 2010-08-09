package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnRingingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiTermConnRingingEventCC extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnRingingEv, ITsapiCallInfo {
	public TsapiTermConnRingingEventCC(TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 218;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnRingingEventCC
 * JD-Core Version: 0.5.4
 */