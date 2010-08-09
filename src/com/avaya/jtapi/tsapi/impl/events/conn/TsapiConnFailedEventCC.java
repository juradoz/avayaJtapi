package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnFailedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiConnFailedEventCC extends TsapiCallCtlConnEvent implements
		CallCtlConnFailedEv, ITsapiCallInfo {
	public TsapiConnFailedEventCC(ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 207;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnFailedEventCC JD-Core
 * Version: 0.5.4
 */