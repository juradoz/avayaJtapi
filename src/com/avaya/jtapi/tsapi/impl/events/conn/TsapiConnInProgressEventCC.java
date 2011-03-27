package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.callcenter.events.CallCentConnInProgressEv;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;

@SuppressWarnings("deprecation")
public class TsapiConnInProgressEventCC extends TsapiCallCtrConnEvent implements
		CallCentConnInProgressEv, ITsapiCallInfo {
	public TsapiConnInProgressEventCC(final ConnEventParams params) {
		super(params);
	}

	@Override
	public final int getID() {
		return 319;
	}
}
