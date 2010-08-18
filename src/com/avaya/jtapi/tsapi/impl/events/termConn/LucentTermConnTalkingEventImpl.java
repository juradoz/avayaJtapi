package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentTermConnTalkingEventImpl extends TsapiTermConnTalkingEvent
		implements LucentCallInfo {
	public LucentTermConnTalkingEventImpl(final TermConnEventParams params) {
		super(params);
	}
}
