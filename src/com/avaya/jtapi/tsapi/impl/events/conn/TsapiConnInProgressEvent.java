package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnInProgressEv;

public final class TsapiConnInProgressEvent extends TsapiConnEvent implements
		ConnInProgressEv {
	public int getID() {
		return 109;
	}

	public TsapiConnInProgressEvent(ConnEventParams params) {
		super(params);
	}
}