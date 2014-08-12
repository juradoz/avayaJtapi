package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnQueuedEventImpl extends
		LucentConnQueuedEventImpl implements LucentV5CallInfo {
	public LucentV5ConnQueuedEventImpl(ConnEventParams params, int numberQueued) {
		super(params, numberQueued);
	}
}