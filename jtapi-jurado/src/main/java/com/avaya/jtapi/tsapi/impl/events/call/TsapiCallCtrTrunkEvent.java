package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.callcenter.CallCenterTrunk;

abstract class TsapiCallCtrTrunkEvent extends TsapiCallEvent {
	public final CallCenterTrunk getTrunk() {
		return this.params.getTrunk();
	}

	TsapiCallCtrTrunkEvent(CallEventParams params) {
		super(params, 2);
	}
}