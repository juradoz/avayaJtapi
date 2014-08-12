package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.events.ProvShutdownEv;

public final class TsapiProviderShutdownEvent extends TsapiProvEvent implements
		ProvShutdownEv {
	public int getID() {
		return 114;
	}

	public TsapiProviderShutdownEvent(Provider _provider, int _cause,
			int _metaCode, Object _privateData) {
		super(_provider, _cause, _metaCode, _privateData);
	}
}