package javax.telephony;

import javax.telephony.events.AddrEv;

/** @deprecated */
public abstract interface AddressObserver {
	public abstract void addressChangedEvent(AddrEv[] paramArrayOfAddrEv);
}