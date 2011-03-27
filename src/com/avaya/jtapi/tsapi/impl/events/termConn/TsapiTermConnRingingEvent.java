package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.events.TermConnRingingEv;

public final class TsapiTermConnRingingEvent extends TsapiTermConnEvent
		implements TermConnRingingEv {
	public TsapiTermConnRingingEvent(final TermConnEventParams params) {
		super(params);
	}

	@Override
	public int getID() {
		return 119;
	}
}
