package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnAlertingEventCCImpl extends TsapiConnAlertingEventCC
		implements LucentCallInfo {
	public LucentConnAlertingEventCCImpl(ConnEventParams params) {
		super(params);
	}
}
