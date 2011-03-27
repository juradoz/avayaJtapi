package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.privatedata.events.PrivateCallEv;

@SuppressWarnings("deprecation")
public final class TsapiPrivateCallEvent extends TsapiCallEvent implements
		PrivateCallEv {
	public TsapiPrivateCallEvent(final CallEventParams params) {
		super(params, 5);
	}

	@Override
	public int getID() {
		return 601;
	}
}
