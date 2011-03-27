package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.events.ProvObservationEndedEv;

public final class TsapiProvObservationEndedEvent extends TsapiProvEvent
		implements ProvObservationEndedEv {
	public TsapiProvObservationEndedEvent(final Provider _provider,
			final int _cause, final Object _privateData) {
		super(_provider, _cause, 136, _privateData);
	}

	@Override
	public int getID() {
		return 112;
	}
}
