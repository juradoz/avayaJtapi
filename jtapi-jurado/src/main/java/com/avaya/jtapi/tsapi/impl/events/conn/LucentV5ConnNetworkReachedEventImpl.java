package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnNetworkReachedEventImpl extends
		LucentConnNetworkReachedEventImpl implements LucentV5CallInfo {
	public LucentV5ConnNetworkReachedEventImpl(ConnEventParams params) {
		super(params);
	}
}