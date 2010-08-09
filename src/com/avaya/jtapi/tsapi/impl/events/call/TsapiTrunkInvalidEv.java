package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.callcenter.events.CallCentTrunkInvalidEv;

public final class TsapiTrunkInvalidEv extends TsapiCallCtrTrunkEvent implements
		CallCentTrunkInvalidEv {
	public TsapiTrunkInvalidEv(CallEventParams params) {
		super(params);
	}

	public int getID() {
		return 318;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.call.TsapiTrunkInvalidEv JD-Core Version:
 * 0.5.4
 */