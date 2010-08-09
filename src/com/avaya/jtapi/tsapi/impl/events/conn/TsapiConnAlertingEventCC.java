package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnAlertingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiConnAlertingEventCC extends TsapiCallCtlConnEvent implements
		CallCtlConnAlertingEv, ITsapiCallInfo {
	public TsapiConnAlertingEventCC(ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 203;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnAlertingEventCC JD-Core
 * Version: 0.5.4
 */