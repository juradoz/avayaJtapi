package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;

public class LucentConnQueuedEventImpl extends TsapiConnQueuedEvent implements
		LucentCallInfo {
	public LucentConnQueuedEventImpl(ConnEventParams params, int numberQueued) {
		super(params, numberQueued);
	}
}
