package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.privatedata.events.PrivateProvEv;

@SuppressWarnings("deprecation")
public final class TsapiPrivateProviderEvent extends TsapiProvEvent implements
		PrivateProvEv {
	public TsapiPrivateProviderEvent(final Provider _provider,
			final int _cause, final int _metaCode, final Object _privateData) {
		super(_provider, _cause, _metaCode, _privateData, 5);
	}

	@Override
	public int getID() {
		return 602;
	}
}
