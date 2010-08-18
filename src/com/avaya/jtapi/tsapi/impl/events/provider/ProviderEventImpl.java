package com.avaya.jtapi.tsapi.impl.events.provider;

import javax.telephony.Provider;
import javax.telephony.ProviderEvent;

import com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent;

public class ProviderEventImpl extends TsapiListenerEvent implements
		ProviderEvent {
	private final Provider provider;

	public ProviderEventImpl(ProviderEventParams params) {
		super(params.getId(), params.getCause(), params.getMetaEvent(), params
				.getSource(), params.getPrivateData());

		provider = params.getProvider();
	}

	public Provider getProvider() {
		return provider;
	}

	@Override
	public String toString() {
		return super.toString() + ";provider=" + provider;
	}
}

