package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnUnknownEventCCImpl extends
		LucentConnUnknownEventCCImpl implements LucentV5CallInfo {
	public LucentV5ConnUnknownEventCCImpl(final ConnEventParams params) {
		super(params);
	}
}
