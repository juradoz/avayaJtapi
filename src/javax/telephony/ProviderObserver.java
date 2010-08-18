package javax.telephony;

import javax.telephony.events.ProvEv;

/** @deprecated */
@Deprecated
public abstract interface ProviderObserver {
	public abstract void providerChangedEvent(ProvEv[] paramArrayOfProvEv);
}
