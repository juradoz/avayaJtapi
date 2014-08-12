package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.events.ProvOutOfServiceEv;

public final class TsapiProviderOutOfServiceEvent extends TsapiProvEvent
		implements ProvOutOfServiceEv {
	public int getID() {
		return 113;
	}

	public TsapiProviderOutOfServiceEvent(Provider _provider, int _cause,
			int _metaCode, Object _privateData) {
		super(_provider, _cause, _metaCode, _privateData);
	}
}