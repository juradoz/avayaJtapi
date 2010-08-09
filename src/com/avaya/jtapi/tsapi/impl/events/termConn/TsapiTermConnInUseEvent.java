package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.callcontrol.events.CallCtlTermConnInUseEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiTermConnInUseEvent extends TsapiCallCtlTermConnEvent
		implements CallCtlTermConnInUseEv, ITsapiCallInfo {
	public TsapiTermConnInUseEvent(TermConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 217;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnInUseEvent JD-Core
 * Version: 0.5.4
 */