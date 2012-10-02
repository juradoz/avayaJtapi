package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnAlertingEv;

public final class TsapiConnAlertingEvent extends TsapiConnEvent implements
		ConnAlertingEv {
	public int getID() {
		return 104;
	}

	public TsapiConnAlertingEvent(ConnEventParams params) {
		super(params);
	}
}