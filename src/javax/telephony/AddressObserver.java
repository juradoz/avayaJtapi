package javax.telephony;

import javax.telephony.events.AddrEv;

/** @deprecated */
@Deprecated
public abstract interface AddressObserver {
	public abstract void addressChangedEvent(AddrEv[] paramArrayOfAddrEv);
}
