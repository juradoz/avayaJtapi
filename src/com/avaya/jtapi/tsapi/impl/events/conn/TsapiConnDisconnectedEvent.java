package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnDisconnectedEv;

public final class TsapiConnDisconnectedEvent extends TsapiConnEvent implements
		ConnDisconnectedEv {
	public TsapiConnDisconnectedEvent(final ConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 107;
	}
}
