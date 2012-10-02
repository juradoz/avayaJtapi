package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.AddressCapabilities;

public abstract interface ACDManagerAddressCapabilities extends
		AddressCapabilities {
	public abstract boolean canGetACDAddresses();
}