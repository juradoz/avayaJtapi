package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.events.CallActiveEv;

public final class TsapiCallActiveEvent extends TsapiCallEvent implements
		CallActiveEv {
	public int getID() {
		return 101;
	}

	public TsapiCallActiveEvent(CallEventParams params) {
		super(params);
	}
}