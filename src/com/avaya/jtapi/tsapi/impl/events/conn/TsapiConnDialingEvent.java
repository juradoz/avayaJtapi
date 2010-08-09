package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnDialingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

public class TsapiConnDialingEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnDialingEv, ITsapiCallInfo {
	private String dialedDigits = null;

	public TsapiConnDialingEvent(ConnEventParams params) {
		super(params);
	}

	public final String getDigits() {
		return dialedDigits;
	}

	public final int getID() {
		return 204;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDialingEvent JD-Core Version:
 * 0.5.4
 */