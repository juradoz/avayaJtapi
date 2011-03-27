package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnInProgressEv;

public final class TsapiConnInProgressEvent extends TsapiConnEvent implements
		ConnInProgressEv {
	public TsapiConnInProgressEvent(final ConnEventParams params) {
		super(params);
	}

	@Override
	public int getID() {
		return 109;
	}
}
