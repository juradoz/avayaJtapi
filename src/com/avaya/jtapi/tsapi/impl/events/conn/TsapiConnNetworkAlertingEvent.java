package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnNetworkAlertingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiConnNetworkAlertingEvent extends TsapiCallCtlConnEvent
		implements CallCtlConnNetworkAlertingEv, ITsapiCallInfo {
	public TsapiConnNetworkAlertingEvent(ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 209;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnNetworkAlertingEvent JD-Core
 * Version: 0.5.4
 */