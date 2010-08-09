package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnDroppedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiTermConnDroppedEventCC extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnDroppedEv, ITsapiCallInfo {
	public TsapiTermConnDroppedEventCC(TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 215;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnDroppedEventCC
 * JD-Core Version: 0.5.4
 */