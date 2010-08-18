package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5ConnEstablishedEventImpl extends
		LucentConnEstablishedEventImpl implements LucentV5CallInfo {
	public LucentV5ConnEstablishedEventImpl(final ConnEventParams params) {
		super(params);
	}
}
