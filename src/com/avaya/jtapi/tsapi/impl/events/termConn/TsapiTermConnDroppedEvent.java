package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnDroppedEv;

public final class TsapiTermConnDroppedEvent extends TsapiTermConnEvent
		implements TermConnDroppedEv {
	public TsapiTermConnDroppedEvent(TermConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 117;
	}
}

