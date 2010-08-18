package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnDialingEventImpl extends TsapiConnDialingEvent implements
		LucentCallInfo {
	public LucentConnDialingEventImpl(final ConnEventParams params) {
		super(params);
	}
}
