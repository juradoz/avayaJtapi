package javax.telephony;

import java.util.EventListener;

public abstract interface ProviderListener extends EventListener {
	public abstract void providerEventTransmissionEnded(
			ProviderEvent paramProviderEvent);

	public abstract void providerInService(ProviderEvent paramProviderEvent);

	public abstract void providerOutOfService(ProviderEvent paramProviderEvent);

	public abstract void providerShutdown(ProviderEvent paramProviderEvent);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.ProviderListener JD-Core Version: 0.5.4
 */