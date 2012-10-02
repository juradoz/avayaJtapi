package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentTermConnBridgedEventImpl extends TsapiTermConnBridgedEvent
		implements LucentCallInfo {
	public LucentTermConnBridgedEventImpl(TermConnEventParams params) {
		super(params);
	}
}