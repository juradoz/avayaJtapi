package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnConnectedEv;

public final class TsapiConnConnectedEvent extends TsapiConnEvent implements
		ConnConnectedEv {
	public int getID() {
		return 105;
	}

	public TsapiConnConnectedEvent(ConnEventParams params) {
		super(params);
	}
}