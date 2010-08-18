package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnUnknownEventCCImpl extends TsapiConnUnknownEventCC
		implements LucentCallInfo {
	public LucentConnUnknownEventCCImpl(final ConnEventParams params) {
		super(params);
	}
}
