package javax.telephony.callcontrol.capabilities;

import javax.telephony.capabilities.AddressCapabilities;

public abstract interface CallControlAddressCapabilities extends
		AddressCapabilities {
	public abstract boolean canCancelForwarding();

	public abstract boolean canGetDoNotDisturb();

	public abstract boolean canGetForwarding();

	public abstract boolean canGetMessageWaiting();

	public abstract boolean canSetDoNotDisturb();

	public abstract boolean canSetForwarding();

	public abstract boolean canSetMessageWaiting();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.capabilities.CallControlAddressCapabilities
 * JD-Core Version: 0.5.4
 */