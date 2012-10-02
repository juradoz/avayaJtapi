package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnActiveEv;

public final class TsapiTermConnActiveEvent extends TsapiTermConnEvent
		implements TermConnActiveEv {
	public int getID() {
		return 115;
	}

	public TsapiTermConnActiveEvent(TermConnEventParams params) {
		super(params);
	}
}