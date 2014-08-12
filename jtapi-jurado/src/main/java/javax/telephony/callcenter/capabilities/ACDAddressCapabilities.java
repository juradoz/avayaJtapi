package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.AddressCapabilities;

public abstract interface ACDAddressCapabilities extends AddressCapabilities {
	public abstract boolean canGetLoggedOnAgents();

	public abstract boolean canGetNumberQueued();

	public abstract boolean canGetOldestCallQueued();

	public abstract boolean canGetRelativeQueueLoad();

	public abstract boolean canGetQueueWaitTime();

	public abstract boolean canGetACDManagerAddress();
}