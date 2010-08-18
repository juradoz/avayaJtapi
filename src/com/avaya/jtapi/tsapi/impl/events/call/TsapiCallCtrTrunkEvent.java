package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.callcenter.CallCenterTrunk;

abstract class TsapiCallCtrTrunkEvent extends TsapiCallEvent {
	TsapiCallCtrTrunkEvent(CallEventParams params) {
		super(params, 2);
	}

	@Override
	public final CallCenterTrunk getTrunk() {
		return params.getTrunk();
	}
}
