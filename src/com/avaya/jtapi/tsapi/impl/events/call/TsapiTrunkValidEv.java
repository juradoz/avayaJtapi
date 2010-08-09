package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.callcenter.events.CallCentTrunkValidEv;

public final class TsapiTrunkValidEv extends TsapiCallCtrTrunkEvent implements
		CallCentTrunkValidEv {
	public TsapiTrunkValidEv(CallEventParams params) {
		super(params);
	}

	public int getID() {
		return 317;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.call.TsapiTrunkValidEv JD-Core Version:
 * 0.5.4
 */