package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnUnknownEv;

@SuppressWarnings("deprecation")
public class TsapiConnUnknownEventCC extends TsapiCallCtlConnEvent implements
		CallCtlConnUnknownEv, ITsapiCallInfo {
	public final int getID() {
		return 213;
	}

	public TsapiConnUnknownEventCC(ConnEventParams params) {
		super(params);
	}
}