package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnInitiatedEv;

@SuppressWarnings("deprecation")
public class TsapiConnInitiatedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnInitiatedEv, ITsapiCallInfo {
	public final int getID() {
		return 208;
	}

	public TsapiConnInitiatedEvent(ConnEventParams params) {
		super(params);
	}
}