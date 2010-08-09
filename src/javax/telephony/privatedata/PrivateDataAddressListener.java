package javax.telephony.privatedata;

import javax.telephony.AddressListener;

public abstract interface PrivateDataAddressListener extends AddressListener {
	public abstract void addressPrivateData(
			PrivateDataEvent paramPrivateDataEvent);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.privatedata.PrivateDataAddressListener JD-Core Version: 0.5.4
 */