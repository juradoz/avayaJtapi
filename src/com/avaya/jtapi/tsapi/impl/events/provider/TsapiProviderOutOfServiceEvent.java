package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.events.ProvOutOfServiceEv;

public final class TsapiProviderOutOfServiceEvent extends TsapiProvEvent
		implements ProvOutOfServiceEv {
	public TsapiProviderOutOfServiceEvent(final Provider _provider,
			final int _cause, final int _metaCode, final Object _privateData) {
		super(_provider, _cause, _metaCode, _privateData);
	}

	@Override
	public int getID() {
		return 113;
	}
}
