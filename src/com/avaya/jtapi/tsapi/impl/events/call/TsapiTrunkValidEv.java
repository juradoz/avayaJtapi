package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.callcenter.events.CallCentTrunkValidEv;

@SuppressWarnings("deprecation")
public final class TsapiTrunkValidEv extends TsapiCallCtrTrunkEvent implements
		CallCentTrunkValidEv {
	public TsapiTrunkValidEv(final CallEventParams params) {
		super(params);
	}

	@Override
	public int getID() {
		return 317;
	}
}
