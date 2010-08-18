package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnUnknownEv;

public final class TsapiTermConnUnknownEvent extends TsapiTermConnEvent
		implements TermConnUnknownEv {
	public TsapiTermConnUnknownEvent(TermConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 120;
	}
}

