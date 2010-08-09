package javax.telephony;

import javax.telephony.events.ProvEv;

/** @deprecated */
@Deprecated
public abstract interface ProviderObserver {
	public abstract void providerChangedEvent(ProvEv[] paramArrayOfProvEv);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.ProviderObserver JD-Core Version: 0.5.4
 */