package javax.telephony.phone.capabilities;

import javax.telephony.Address;

public abstract interface ComponentGroupCapabilities {
	public abstract boolean canActivate();

	public abstract boolean canActivate(Address paramAddress);
}

