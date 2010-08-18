package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.AddressCapabilities;

public abstract interface RouteAddressCapabilities extends AddressCapabilities {
	public abstract boolean canRouteCalls();
}

