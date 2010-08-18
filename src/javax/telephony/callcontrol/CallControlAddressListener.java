package javax.telephony.callcontrol;

import javax.telephony.AddressListener;

public abstract interface CallControlAddressListener extends AddressListener {
	public abstract void addressDoNotDisturb(
			CallControlAddressEvent paramCallControlAddressEvent);

	public abstract void addressForwarded(
			CallControlAddressEvent paramCallControlAddressEvent);

	public abstract void addressMessageWaiting(
			CallControlAddressEvent paramCallControlAddressEvent);
}
