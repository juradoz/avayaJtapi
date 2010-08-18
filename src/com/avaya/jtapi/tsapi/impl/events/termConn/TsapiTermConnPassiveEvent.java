package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnPassiveEv;

public final class TsapiTermConnPassiveEvent extends TsapiTermConnEvent
		implements TermConnPassiveEv {
	public TsapiTermConnPassiveEvent(final TermConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 118;
	}
}
