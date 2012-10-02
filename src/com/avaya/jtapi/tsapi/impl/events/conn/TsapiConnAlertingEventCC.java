package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnAlertingEv;

@SuppressWarnings("deprecation")
public class TsapiConnAlertingEventCC extends TsapiCallCtlConnEvent implements
		CallCtlConnAlertingEv, ITsapiCallInfo {
	public final int getID() {
		return 203;
	}

	public TsapiConnAlertingEventCC(ConnEventParams params) {
		super(params);
	}
}