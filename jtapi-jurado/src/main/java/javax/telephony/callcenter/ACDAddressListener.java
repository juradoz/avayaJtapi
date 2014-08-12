package javax.telephony.callcenter;

import javax.telephony.AddressListener;

public abstract interface ACDAddressListener extends AddressListener {
	public abstract void acdAddressBusy(ACDAddressEvent paramACDAddressEvent);

	public abstract void acdAddressLoggedOff(
			ACDAddressEvent paramACDAddressEvent);

	public abstract void acdAddressLoggedOn(ACDAddressEvent paramACDAddressEvent);

	public abstract void acdAddressNotReady(ACDAddressEvent paramACDAddressEvent);

	public abstract void acdAddressReady(ACDAddressEvent paramACDAddressEvent);

	public abstract void acdAddressUnknown(ACDAddressEvent paramACDAddressEvent);

	public abstract void acdAddressWorkNotReady(
			ACDAddressEvent paramACDAddressEvent);

	public abstract void acdAddressWorkReady(
			ACDAddressEvent paramACDAddressEvent);
}