package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnAlertingEventCCImpl extends
		LucentConnAlertingEventCCImpl implements LucentV5CallInfo {
	public LucentV5ConnAlertingEventCCImpl(ConnEventParams params) {
		super(params);
	}
}