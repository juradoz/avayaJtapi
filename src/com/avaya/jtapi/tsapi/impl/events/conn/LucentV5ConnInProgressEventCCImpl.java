package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnInProgressEventCCImpl extends
		LucentConnInProgressEventCCImpl implements LucentV5CallInfo {
	public LucentV5ConnInProgressEventCCImpl(final ConnEventParams params) {
		super(params);
	}
}
