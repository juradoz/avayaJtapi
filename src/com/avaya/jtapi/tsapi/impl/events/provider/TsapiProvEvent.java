package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.privatedata.events.PrivateProvEv;

import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;

@SuppressWarnings("deprecation")
public abstract class TsapiProvEvent extends TsapiObserverEvent implements
		PrivateProvEv {
	Provider jtapi_provider = null;

	TsapiProvEvent(Provider _provider, int _cause, int _metaCode,
			Object _privateData) {
		this(_provider, _cause, _metaCode, _privateData, 0);
	}

	public TsapiProvEvent(Provider _provider, int _cause, int _metaCode,
			Object _privateData, int _eventPackage) {
		super(_cause, _metaCode, _privateData, _eventPackage);
		jtapi_provider = _provider;
	}

	public final Provider getProvider() {
		return jtapi_provider;
	}
}

