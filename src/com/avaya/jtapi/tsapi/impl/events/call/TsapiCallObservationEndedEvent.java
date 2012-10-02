package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.events.CallObservationEndedEv;

public final class TsapiCallObservationEndedEvent extends TsapiCallEvent
		implements CallObservationEndedEv {
	Object observed;

	public int getID() {
		return 103;
	}

	public Object getEndedObject() {
		return this.observed;
	}

	public TsapiCallObservationEndedEvent(CallEventParams params,
			Object _observed) {
		super(params);
		this.observed = _observed;
	}
}