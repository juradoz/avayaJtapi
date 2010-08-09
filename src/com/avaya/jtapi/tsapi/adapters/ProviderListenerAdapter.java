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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.adapters.ProviderListenerAdapter JD-Core Version: 0.5.4
 */