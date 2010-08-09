package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.events.ProvShutdownEv;

public final class TsapiProviderShutdownEvent extends TsapiProvEvent implements
		ProvShutdownEv {
	public TsapiProviderShutdownEvent(Provider _provider, int _cause,
			int _metaCode, Object _privateData) {
		super(_provider, _cause, _metaCode, _privateData);
	}

	public int getID() {
		return 114;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderShutdownEvent JD-Core
 * Version: 0.5.4
 */