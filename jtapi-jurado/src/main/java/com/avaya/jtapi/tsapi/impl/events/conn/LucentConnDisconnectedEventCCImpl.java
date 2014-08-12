package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnDisconnectedEventCCImpl extends
		TsapiConnDisconnectedEventCC implements LucentCallInfo {
	public LucentConnDisconnectedEventCCImpl(ConnEventParams params) {
		super(params);
	}
}