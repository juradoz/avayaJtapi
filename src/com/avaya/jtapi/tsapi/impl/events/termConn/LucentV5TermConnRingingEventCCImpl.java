package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5TermConnRingingEventCCImpl extends
		LucentTermConnRingingEventCCImpl implements LucentV5CallInfo {
	public LucentV5TermConnRingingEventCCImpl(final TermConnEventParams params) {
		super(params);
	}
}
