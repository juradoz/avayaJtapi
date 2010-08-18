package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnInitiatedEventImpl extends TsapiConnInitiatedEvent
		implements LucentCallInfo {
	public LucentConnInitiatedEventImpl(ConnEventParams params) {
		super(params);
	}
}
