package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.events.CallActiveEv;

public final class TsapiCallActiveEvent extends TsapiCallEvent implements
		CallActiveEv {
	public TsapiCallActiveEvent(CallEventParams params) {
		super(params);
	}

	public int getID() {
		return 101;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.call.TsapiCallActiveEvent JD-Core Version:
 * 0.5.4
 */