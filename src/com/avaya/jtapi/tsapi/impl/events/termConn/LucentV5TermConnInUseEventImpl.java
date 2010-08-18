package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5TermConnInUseEventImpl extends
		LucentTermConnInUseEventImpl implements LucentV5CallInfo {
	public LucentV5TermConnInUseEventImpl(final TermConnEventParams params) {
		super(params);
	}
}
