package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import com.avaya.jtapi.tsapi.ITsapiConnNetworkReachedEvent;
import com.avaya.jtapi.tsapi.NetworkProgressInfo;

public class TsapiConnNetworkReachedEvent extends TsapiCallCtlConnEvent
		implements ITsapiConnNetworkReachedEvent, ITsapiCallInfo {
	public TsapiConnNetworkReachedEvent(final ConnEventParams params) {
		super(params);
	}

	@Override
	public final int getID() {
		return 210;
	}

	public final NetworkProgressInfo getNetworkProgressInfo() {
		if (privateData instanceof NetworkProgressInfo)
			return (NetworkProgressInfo) privateData;
		return null;
	}
}
