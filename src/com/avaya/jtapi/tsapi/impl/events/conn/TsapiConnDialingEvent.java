package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnDialingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnDialingEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnDialingEv, ITsapiCallInfo {
	private final String dialedDigits = null;

	public TsapiConnDialingEvent(final ConnEventParams params) {
		super(params);
	}

	@Override
	public final String getDigits() {
		return dialedDigits;
	}

	@Override
	public final int getID() {
		return 204;
	}
}
