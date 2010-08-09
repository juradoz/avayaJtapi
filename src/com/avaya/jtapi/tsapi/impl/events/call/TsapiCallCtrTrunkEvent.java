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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.call.TsapiCallCtrTrunkEvent JD-Core
 * Version: 0.5.4
 */