package javax.telephony.events;

import javax.telephony.Address;

public abstract interface AddrEv extends Ev {
	public abstract Address getAddress();
}