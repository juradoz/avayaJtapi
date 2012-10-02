package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5TermConnUnknownEventCCImpl extends
		LucentTermConnUnknownEventCCImpl implements LucentV5CallInfo {
	public LucentV5TermConnUnknownEventCCImpl(TermConnEventParams params) {
		super(params);
	}
}