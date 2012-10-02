package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnFailedEv;

public final class TsapiConnFailedEvent extends TsapiConnEvent implements
		ConnFailedEv {
	public int getID() {
		return 108;
	}

	public TsapiConnFailedEvent(ConnEventParams params) {
		super(params);
	}
}