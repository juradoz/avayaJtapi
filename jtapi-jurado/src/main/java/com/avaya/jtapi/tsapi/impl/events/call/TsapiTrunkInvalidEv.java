package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.callcenter.events.CallCentTrunkInvalidEv;

@SuppressWarnings("deprecation")
public final class TsapiTrunkInvalidEv extends TsapiCallCtrTrunkEvent implements
		CallCentTrunkInvalidEv {
	public int getID() {
		return 318;
	}

	public TsapiTrunkInvalidEv(CallEventParams params) {
		super(params);
	}
}