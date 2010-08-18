package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.AddressCapabilities;

public abstract interface CallCenterAddressCapabilities extends
		AddressCapabilities {
	public abstract boolean canAddCallObserver(boolean paramBoolean);
}

