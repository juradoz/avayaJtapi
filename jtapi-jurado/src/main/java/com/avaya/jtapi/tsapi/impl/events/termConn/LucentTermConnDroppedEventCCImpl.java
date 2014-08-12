package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentTermConnDroppedEventCCImpl extends
		TsapiTermConnDroppedEventCC implements LucentCallInfo {
	public LucentTermConnDroppedEventCCImpl(TermConnEventParams params) {
		super(params);
	}
}