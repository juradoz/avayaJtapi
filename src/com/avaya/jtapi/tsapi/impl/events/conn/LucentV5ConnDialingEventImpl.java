package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnDialingEventImpl extends
		LucentConnDialingEventImpl implements LucentV5CallInfo {
	public LucentV5ConnDialingEventImpl(ConnEventParams params) {
		super(params);
	}
}
