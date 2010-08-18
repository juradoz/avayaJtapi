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
