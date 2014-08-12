package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnOfferedEv;

@SuppressWarnings("deprecation")
public class TsapiConnOfferedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnOfferedEv, ITsapiCallInfo {
	public final int getID() {
		return 211;
	}

	public TsapiConnOfferedEvent(ConnEventParams params) {
		super(params);
	}
}