package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnInitiatedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnInitiatedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnInitiatedEv, ITsapiCallInfo {
	public TsapiConnInitiatedEvent(ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 208;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnInitiatedEvent JD-Core
 * Version: 0.5.4
 */