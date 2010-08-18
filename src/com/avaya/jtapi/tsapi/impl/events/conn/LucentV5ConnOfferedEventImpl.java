package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnOfferedEventImpl extends
		LucentConnOfferedEventImpl implements LucentV5CallInfo {
	public LucentV5ConnOfferedEventImpl(final ConnEventParams params) {
		super(params);
	}
}
