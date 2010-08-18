package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnAlertingEventCCImpl extends TsapiConnAlertingEventCC
		implements LucentCallInfo {
	public LucentConnAlertingEventCCImpl(final ConnEventParams params) {
		super(params);
	}
}
