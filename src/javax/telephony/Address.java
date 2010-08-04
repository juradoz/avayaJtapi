package javax.telephony;

import javax.telephony.capabilities.AddressCapabilities;

public abstract interface Address
{
  public abstract String getName();

  public abstract Provider getProvider();

  public abstract Terminal[] getTerminals();

  public abstract Connection[] getConnections();

  public abstract void addObserver(AddressObserver paramAddressObserver)
    throws ResourceUnavailableException, MethodNotSupportedException;

  public abstract AddressObserver[] getObservers();

  public abstract void removeObserver(AddressObserver paramAddressObserver);

  public abstract void addCallObserver(CallObserver paramCallObserver)
    throws ResourceUnavailableException, MethodNotSupportedException;

  public abstract CallObserver[] getCallObservers();

  public abstract void removeCallObserver(CallObserver paramCallObserver);

  public abstract AddressCapabilities getCapabilities();

  /** @deprecated */
  public abstract AddressCapabilities getAddressCapabilities(Terminal paramTerminal)
    throws InvalidArgumentException, PlatformException;

  public abstract void addAddressListener(AddressListener paramAddressListener)
    throws ResourceUnavailableException, MethodNotSupportedException;

  public abstract AddressListener[] getAddressListeners();

  public abstract void removeAddressListener(AddressListener paramAddressListener);

  public abstract void addCallListener(CallListener paramCallListener)
    throws ResourceUnavailableException, MethodNotSupportedException;

  public abstract CallListener[] getCallListeners();

  public abstract void removeCallListener(CallListener paramCallListener);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.Address
 * JD-Core Version:    0.5.4
 */