package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcontrol.events.CallCtlConnNetworkAlertingEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnNetworkAlertingEvent extends TsapiCallCtlConnEvent
		implements CallCtlConnNetworkAlertingEv, ITsapiCallInfo {
	public TsapiConnNetworkAlertingEvent(final ConnEventParams params) {
		super(params);
	}

	public final int getID() {
		return 209;
	}
}
