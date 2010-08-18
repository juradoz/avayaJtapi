package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnInitiatedEventImpl extends
		LucentConnInitiatedEventImpl implements LucentV5CallInfo {
	public LucentV5ConnInitiatedEventImpl(final ConnEventParams params) {
		super(params);
	}
}
