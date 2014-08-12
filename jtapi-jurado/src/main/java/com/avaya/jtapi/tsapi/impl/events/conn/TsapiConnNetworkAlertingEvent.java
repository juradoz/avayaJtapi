package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnNetworkAlertingEv;

@SuppressWarnings("deprecation")
public class TsapiConnNetworkAlertingEvent extends TsapiCallCtlConnEvent
		implements CallCtlConnNetworkAlertingEv, ITsapiCallInfo {
	public final int getID() {
		return 209;
	}

	public TsapiConnNetworkAlertingEvent(ConnEventParams params) {
		super(params);
	}
}