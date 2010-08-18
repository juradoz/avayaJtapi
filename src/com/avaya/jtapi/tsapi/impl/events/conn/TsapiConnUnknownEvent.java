package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnUnknownEv;

public final class TsapiConnUnknownEvent extends TsapiConnEvent implements
		ConnUnknownEv {
	public TsapiConnUnknownEvent(final ConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 110;
	}
}
