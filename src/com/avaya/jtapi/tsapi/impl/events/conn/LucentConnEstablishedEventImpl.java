package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnEstablishedEventImpl extends TsapiConnEstablishedEvent
		implements LucentCallInfo {
	public LucentConnEstablishedEventImpl(final ConnEventParams params) {
		super(params);
	}
}
