package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentTermConnRingingEventCCImpl extends
		TsapiTermConnRingingEventCC implements LucentCallInfo {
	public LucentTermConnRingingEventCCImpl(TermConnEventParams params) {
		super(params);
	}
}