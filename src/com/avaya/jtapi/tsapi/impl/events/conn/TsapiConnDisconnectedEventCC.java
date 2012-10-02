package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnDisconnectedEv;

@SuppressWarnings("deprecation")
public class TsapiConnDisconnectedEventCC extends TsapiCallCtlConnEvent
		implements CallCtlConnDisconnectedEv, ITsapiCallInfo {
	public final int getID() {
		return 205;
	}

	public TsapiConnDisconnectedEventCC(ConnEventParams params) {
		super(params);
	}
}