package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import javax.telephony.callcenter.events.CallCentConnInProgressEv;

@SuppressWarnings("deprecation")
public class TsapiConnInProgressEventCC extends TsapiCallCtrConnEvent implements
		CallCentConnInProgressEv, ITsapiCallInfo {
	public final int getID() {
		return 319;
	}

	public TsapiConnInProgressEventCC(ConnEventParams params) {
		super(params);
	}
}