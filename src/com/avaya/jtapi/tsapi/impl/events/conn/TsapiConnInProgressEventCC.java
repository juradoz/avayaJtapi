package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcenter.events.CallCentConnInProgressEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnInProgressEventCC extends TsapiCallCtrConnEvent implements
		CallCentConnInProgressEv, ITsapiCallInfo {
	public TsapiConnInProgressEventCC(ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 319;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnInProgressEventCC JD-Core
 * Version: 0.5.4
 */