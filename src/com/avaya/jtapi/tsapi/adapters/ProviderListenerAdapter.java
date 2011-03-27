package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.ProviderEvent;
import javax.telephony.ProviderListener;

public abstract class ProviderListenerAdapter implements ProviderListener {
	@Override
	public void providerEventTransmissionEnded(final ProviderEvent event) {
	}

	@Override
	public void providerInService(final ProviderEvent event) {
	}

	@Override
	public void providerOutOfService(final ProviderEvent event) {
	}

	@Override
	public void providerShutdown(final ProviderEvent event) {
	}
}
