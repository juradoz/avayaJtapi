package javax.telephony.privatedata;

import javax.telephony.AddressListener;

public abstract interface PrivateDataAddressListener extends AddressListener {
	public abstract void addressPrivateData(
			PrivateDataEvent paramPrivateDataEvent);
}