package javax.telephony;

import java.util.EventListener;

public abstract interface ProviderListener extends EventListener {
	public abstract void providerEventTransmissionEnded(
			ProviderEvent paramProviderEvent);

	public abstract void providerInService(ProviderEvent paramProviderEvent);

	public abstract void providerOutOfService(ProviderEvent paramProviderEvent);

	public abstract void providerShutdown(ProviderEvent paramProviderEvent);
}

