package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentTermConnUnknownEventCCImpl extends
		TsapiTermConnUnknownEventCC implements LucentCallInfo {
	public LucentTermConnUnknownEventCCImpl(final TermConnEventParams params) {
		super(params);
	}
}
