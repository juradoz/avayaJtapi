package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentTermConnHeldEventImpl extends TsapiTermConnHeldEvent
		implements LucentCallInfo {
	public LucentTermConnHeldEventImpl(TermConnEventParams params) {
		super(params);
	}
}

