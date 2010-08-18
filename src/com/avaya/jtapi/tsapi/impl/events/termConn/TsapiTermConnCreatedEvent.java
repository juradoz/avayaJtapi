package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnCreatedEv;

public final class TsapiTermConnCreatedEvent extends TsapiTermConnEvent
		implements TermConnCreatedEv {
	public TsapiTermConnCreatedEvent(TermConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 116;
	}
}

