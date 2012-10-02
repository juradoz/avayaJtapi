package javax.telephony.callcontrol.capabilities;

import javax.telephony.capabilities.AddressCapabilities;

public abstract interface CallControlAddressCapabilities extends
		AddressCapabilities {
	public abstract boolean canSetForwarding();

	public abstract boolean canGetForwarding();

	public abstract boolean canCancelForwarding();

	public abstract boolean canGetDoNotDisturb();

	public abstract boolean canSetDoNotDisturb();

	public abstract boolean canGetMessageWaiting();

	public abstract boolean canSetMessageWaiting();
}