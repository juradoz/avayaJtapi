package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnCreatedEv;

public final class TsapiConnCreatedEvent extends TsapiConnEvent implements
		ConnCreatedEv {
	public TsapiConnCreatedEvent(final ConnEventParams params) {
		super(params);
	}

	@Override
	public int getID() {
		return 106;
	}
}
