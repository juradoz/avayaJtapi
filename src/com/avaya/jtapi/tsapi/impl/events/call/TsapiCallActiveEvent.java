package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.events.CallActiveEv;

public final class TsapiCallActiveEvent extends TsapiCallEvent implements
		CallActiveEv {
	public TsapiCallActiveEvent(final CallEventParams params) {
		super(params);
	}

	public int getID() {
		return 101;
	}
}
