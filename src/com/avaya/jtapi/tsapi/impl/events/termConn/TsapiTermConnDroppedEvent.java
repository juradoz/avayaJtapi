package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnDroppedEv;

public final class TsapiTermConnDroppedEvent extends TsapiTermConnEvent
		implements TermConnDroppedEv {
	public TsapiTermConnDroppedEvent(final TermConnEventParams params) {
		super(params);
	}

	@Override
	public int getID() {
		return 117;
	}
}
