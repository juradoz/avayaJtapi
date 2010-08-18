package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.ProviderEvent;
import javax.telephony.ProviderListener;

public abstract class ProviderListenerAdapter implements ProviderListener {
	public void providerEventTransmissionEnded(final ProviderEvent event) {
	}

	public void providerInService(final ProviderEvent event) {
	}

	public void providerOutOfService(final ProviderEvent event) {
	}

	public void providerShutdown(final ProviderEvent event) {
	}
}
