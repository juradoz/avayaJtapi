package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnDisconnectedEventCCImpl extends
		LucentConnDisconnectedEventCCImpl implements LucentV5CallInfo {
	public LucentV5ConnDisconnectedEventCCImpl(ConnEventParams params) {
		super(params);
	}
}
