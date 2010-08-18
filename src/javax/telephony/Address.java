package javax.telephony;

import javax.telephony.capabilities.AddressCapabilities;

@SuppressWarnings("deprecation")
public abstract interface Address {
	public abstract void addAddressListener(AddressListener paramAddressListener)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract void addCallListener(CallListener paramCallListener)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract void addCallObserver(CallObserver paramCallObserver)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract void addObserver(AddressObserver paramAddressObserver)
			throws ResourceUnavailableException, MethodNotSupportedException;

	/** @deprecated */
	@Deprecated
	public abstract AddressCapabilities getAddressCapabilities(
			Terminal paramTerminal) throws InvalidArgumentException,
			PlatformException;

	public abstract AddressListener[] getAddressListeners();

	public abstract CallListener[] getCallListeners();

	public abstract CallObserver[] getCallObservers();

	public abstract AddressCapabilities getCapabilities();

	public abstract Connection[] getConnections();

	public abstract String getName();

	public abstract AddressObserver[] getObservers();

	public abstract Provider getProvider();

	public abstract Terminal[] getTerminals();

	public abstract void removeAddressListener(
			AddressListener paramAddressListener);

	public abstract void removeCallListener(CallListener paramCallListener);

	public abstract void removeCallObserver(CallObserver paramCallObserver);

	public abstract void removeObserver(AddressObserver paramAddressObserver);
}
