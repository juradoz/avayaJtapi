package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentTermConnInUseEventImpl extends TsapiTermConnInUseEvent
		implements LucentCallInfo {
	public LucentTermConnInUseEventImpl(final TermConnEventParams params) {
		super(params);
	}
}
