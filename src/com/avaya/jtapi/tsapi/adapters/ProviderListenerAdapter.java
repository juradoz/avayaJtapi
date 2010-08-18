package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.ProviderEvent;
import javax.telephony.ProviderListener;

public abstract class ProviderListenerAdapter implements ProviderListener {
	public void providerEventTransmissionEnded(ProviderEvent event) {
	}

	public void providerInService(ProviderEvent event) {
	}

	public void providerOutOfService(ProviderEvent event) {
	}

	public void providerShutdown(ProviderEvent event) {
	}
}
