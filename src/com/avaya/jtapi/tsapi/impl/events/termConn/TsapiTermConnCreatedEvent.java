package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnCreatedEv;

public final class TsapiTermConnCreatedEvent extends TsapiTermConnEvent
		implements TermConnCreatedEv {
	public TsapiTermConnCreatedEvent(final TermConnEventParams params) {
		super(params);
	}

	@Override
	public int getID() {
		return 116;
	}
}
