package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnRingingEv;

public final class TsapiTermConnRingingEvent extends TsapiTermConnEvent
		implements TermConnRingingEv {
	public int getID() {
		return 119;
	}

	public TsapiTermConnRingingEvent(TermConnEventParams params) {
		super(params);
	}
}