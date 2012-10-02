package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.events.CallInvalidEv;

public final class TsapiCallInvalidEvent extends TsapiCallEvent implements
		CallInvalidEv {
	public int getID() {
		return 102;
	}

	public TsapiCallInvalidEvent(CallEventParams params) {
		super(params);
	}
}