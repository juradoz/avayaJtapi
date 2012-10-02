package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnFailedEv;

@SuppressWarnings("deprecation")
public class TsapiConnFailedEventCC extends TsapiCallCtlConnEvent implements
		CallCtlConnFailedEv, ITsapiCallInfo {
	public final int getID() {
		return 207;
	}

	public TsapiConnFailedEventCC(ConnEventParams params) {
		super(params);
	}
}