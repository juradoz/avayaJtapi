package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnDisconnectedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiConnDisconnectedEventCC extends TsapiCallCtlConnEvent
		implements CallCtlConnDisconnectedEv, ITsapiCallInfo {
	public TsapiConnDisconnectedEventCC(ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 205;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDisconnectedEventCC JD-Core
 * Version: 0.5.4
 */