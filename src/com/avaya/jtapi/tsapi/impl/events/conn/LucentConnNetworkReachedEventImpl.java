package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;
import com.avaya.jtapi.tsapi.LucentConnNetworkReachedEvent;

public class LucentConnNetworkReachedEventImpl extends
		TsapiConnNetworkReachedEvent implements LucentConnNetworkReachedEvent,
		LucentCallInfo {
	public LucentConnNetworkReachedEventImpl(ConnEventParams params) {
		super(params);
	}
}
