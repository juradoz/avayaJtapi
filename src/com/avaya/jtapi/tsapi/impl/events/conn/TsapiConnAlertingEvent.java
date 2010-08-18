package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnAlertingEv;

public final class TsapiConnAlertingEvent extends TsapiConnEvent implements
		ConnAlertingEv {
	public TsapiConnAlertingEvent(final ConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 104;
	}
}
