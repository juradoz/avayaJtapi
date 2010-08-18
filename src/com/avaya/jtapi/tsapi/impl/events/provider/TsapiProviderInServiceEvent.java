package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.events.ProvInServiceEv;

public final class TsapiProviderInServiceEvent extends TsapiProvEvent implements
		ProvInServiceEv {
	public TsapiProviderInServiceEvent(Provider _provider, int _cause,
			int _metaCode, Object _privateData) {
		super(_provider, _cause, _metaCode, _privateData);
	}

	public int getID() {
		return 111;
	}
}

