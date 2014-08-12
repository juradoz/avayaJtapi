package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcontrol.events.CallCtlConnEstablishedEv;

@SuppressWarnings("deprecation")
public class TsapiConnEstablishedEvent extends TsapiCallCtlConnEvent implements
		CallCtlConnEstablishedEv, ITsapiCallInfo {
	public final int getID() {
		return 206;
	}

	public TsapiConnEstablishedEvent(ConnEventParams params) {
		super(params);
	}
}