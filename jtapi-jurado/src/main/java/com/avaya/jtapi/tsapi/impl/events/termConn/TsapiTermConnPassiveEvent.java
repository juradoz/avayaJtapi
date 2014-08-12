package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnPassiveEv;

public final class TsapiTermConnPassiveEvent extends TsapiTermConnEvent
		implements TermConnPassiveEv {
	public int getID() {
		return 118;
	}

	public TsapiTermConnPassiveEvent(TermConnEventParams params) {
		super(params);
	}
}