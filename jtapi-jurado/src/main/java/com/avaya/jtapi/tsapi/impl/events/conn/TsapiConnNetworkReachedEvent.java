package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import com.avaya.jtapi.tsapi.ITsapiConnNetworkReachedEvent;
import com.avaya.jtapi.tsapi.NetworkProgressInfo;

public class TsapiConnNetworkReachedEvent extends TsapiCallCtlConnEvent
		implements ITsapiConnNetworkReachedEvent, ITsapiCallInfo {
	public final NetworkProgressInfo getNetworkProgressInfo() {
		if ((this.privateData instanceof NetworkProgressInfo)) {
			return (NetworkProgressInfo) this.privateData;
		}
		return null;
	}

	public final int getID() {
		return 210;
	}

	public TsapiConnNetworkReachedEvent(ConnEventParams params) {
		super(params);
	}
}