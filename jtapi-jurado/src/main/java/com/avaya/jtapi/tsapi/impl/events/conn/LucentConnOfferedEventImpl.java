package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnOfferedEventImpl extends TsapiConnOfferedEvent implements
		LucentCallInfo {
	public LucentConnOfferedEventImpl(ConnEventParams params) {
		super(params);
	}
}