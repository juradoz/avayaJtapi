package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5TermConnBridgedEventImpl extends
		LucentTermConnBridgedEventImpl implements LucentV5CallInfo {
	public LucentV5TermConnBridgedEventImpl(final TermConnEventParams params) {
		super(params);
	}
}
