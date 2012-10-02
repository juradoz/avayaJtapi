package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnNetworkAlertingEventImpl extends
		LucentConnNetworkAlertingEventImpl implements LucentV5CallInfo {
	public LucentV5ConnNetworkAlertingEventImpl(ConnEventParams params) {
		super(params);
	}
}