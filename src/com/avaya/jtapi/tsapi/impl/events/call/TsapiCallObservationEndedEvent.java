package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.events.CallObservationEndedEv;

public final class TsapiCallObservationEndedEvent extends TsapiCallEvent
		implements CallObservationEndedEv {
	Object observed;

	public TsapiCallObservationEndedEvent(CallEventParams params,
			Object _observed) {
		super(params);
		observed = _observed;
	}

	public Object getEndedObject() {
		return observed;
	}

	public int getID() {
		return 103;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.call.TsapiCallObservationEndedEvent JD-Core
 * Version: 0.5.4
 */