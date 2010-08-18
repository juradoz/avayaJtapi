package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnEstablishedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnEstablishedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnEstablishedEv, ITsapiCallInfo {
	public TsapiConnEstablishedEvent(ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 206;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnEstablishedEvent JD-Core
 * Version: 0.5.4
 */