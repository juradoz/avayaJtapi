package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5TermConnDroppedEventCCImpl extends
		LucentTermConnDroppedEventCCImpl implements LucentV5CallInfo {
	public LucentV5TermConnDroppedEventCCImpl(TermConnEventParams params) {
		super(params);
	}
}

