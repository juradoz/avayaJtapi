package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnUnknownEv;

public final class TsapiTermConnUnknownEvent extends TsapiTermConnEvent
		implements TermConnUnknownEv {
	public int getID() {
		return 120;
	}

	public TsapiTermConnUnknownEvent(TermConnEventParams params) {
		super(params);
	}
}