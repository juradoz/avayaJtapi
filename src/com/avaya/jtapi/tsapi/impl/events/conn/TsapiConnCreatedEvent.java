package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnCreatedEv;

public final class TsapiConnCreatedEvent extends TsapiConnEvent implements
		ConnCreatedEv {
	public int getID() {
		return 106;
	}

	public TsapiConnCreatedEvent(ConnEventParams params) {
		super(params);
	}
}