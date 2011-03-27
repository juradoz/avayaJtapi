package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnOfferedEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnOfferedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnOfferedEv, ITsapiCallInfo {
	public TsapiConnOfferedEvent(final ConnEventParams params) {
		super(params);
	}

	@Override
	public final int getID() {
		return 211;
	}
}
