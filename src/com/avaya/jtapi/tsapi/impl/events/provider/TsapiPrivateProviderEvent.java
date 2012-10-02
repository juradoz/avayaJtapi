package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.privatedata.events.PrivateProvEv;

@SuppressWarnings("deprecation")
public final class TsapiPrivateProviderEvent extends TsapiProvEvent implements
		PrivateProvEv {
	public int getID() {
		return 602;
	}

	public TsapiPrivateProviderEvent(Provider _provider, int _cause,
			int _metaCode, Object _privateData) {
		super(_provider, _cause, _metaCode, _privateData, 5);
	}
}