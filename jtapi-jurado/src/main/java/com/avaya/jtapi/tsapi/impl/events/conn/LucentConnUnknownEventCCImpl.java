package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnUnknownEventCCImpl extends TsapiConnUnknownEventCC
		implements LucentCallInfo {
	public LucentConnUnknownEventCCImpl(ConnEventParams params) {
		super(params);
	}
}