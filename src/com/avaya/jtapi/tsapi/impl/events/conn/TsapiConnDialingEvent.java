package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnDialingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
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

