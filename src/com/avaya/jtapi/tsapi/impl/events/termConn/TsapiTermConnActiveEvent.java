package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnActiveEv;

public final class TsapiTermConnActiveEvent extends TsapiTermConnEvent
		implements TermConnActiveEv {
	public TsapiTermConnActiveEvent(final TermConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 115;
	}
}
