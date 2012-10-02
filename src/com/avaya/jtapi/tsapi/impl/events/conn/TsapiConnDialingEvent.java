package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnDialingEv;

@SuppressWarnings("deprecation")
public class TsapiConnDialingEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnDialingEv, ITsapiCallInfo {
	private String dialedDigits = null;

	public final int getID() {
		return 204;
	}

	public final String getDigits() {
		return this.dialedDigits;
	}

	public TsapiConnDialingEvent(ConnEventParams params) {
		super(params);
	}
}