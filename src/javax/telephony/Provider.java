package javax.telephony;

import javax.telephony.capabilities.AddressCapabilities;
import javax.telephony.capabilities.CallCapabilities;
import javax.telephony.capabilities.ConnectionCapabilities;
import javax.telephony.capabilities.ProviderCapabilities;
import javax.telephony.capabilities.TerminalCapabilities;
import javax.telephony.capabilities.TerminalConnectionCapabilities;

public abstract interface Provider
{
  public static final int IN_SERVICE = 16;
  public static final int OUT_OF_SERVICE = 17;
  public static final int SHUTDOWN = 18;

  public abstract int getState();

  public abstract String getName();

  public abstract Call[] getCalls()
    throws ResourceUnavailableException;

  public abstract Address getAddress(String paramString)
    throws InvalidArgumentException;

  public abstract Address[] getAddresses()
    throws ResourceUnavailableException;

  public abstract Terminal[] getTerminals()
    throws ResourceUnavailableException;

  public abstract Terminal getTerminal(String paramString)
    throws InvalidArgumentException;

  public abstract void shutdown();

  public abstract Call createCall()
    throws ResourceUnavailableException, InvalidStateException, PrivilegeViolationException, MethodNotSupportedException;

  public abstract void addObserver(ProviderObserver paramProviderObserver)
    throws ResourceUnavailableException, MethodNotSupportedException;

  public abstract ProviderObserver[] getObservers();

  public abstract void removeObserver(ProviderObserver paramProviderObserver);

  public abstract ProviderCapabilities getProviderCapabilities();

  public abstract CallCapabilities getCallCapabilities();

  public abstract AddressCapabilities getAddressCapabilities();

  public abstract TerminalCapabilities getTerminalCapabilities();

  public abstract ConnectionCapabilities getConnectionCapabilities();

  public abstract TerminalConnectionCapabilities getTerminalConnectionCapabilities();

  public abstract ProviderCapabilities getCapabilities();

  /** @deprecated */
  public abstract ProviderCapabilities getProviderCapabilities(Terminal paramTerminal)
    throws InvalidArgumentException, PlatformException;

  /** @deprecated */
  public abstract CallCapabilities getCallCapabilities(Terminal paramTerminal, Address paramAddress)
    throws InvalidArgumentException, PlatformException;

  /** @deprecated */
  public abstract ConnectionCapabilities getConnectionCapabilities(Terminal paramTerminal, Address paramAddress)
    throws InvalidArgumentException, PlatformException;

  /** @deprecated */
  public abstract AddressCapabilities getAddressCapabilities(Terminal paramTerminal)
    throws InvalidArgumentException, PlatformException;

  /** @deprecated */
  public abstract TerminalConnectionCapabilities getTerminalConnectionCapabilities(Terminal paramTerminal)
    throws InvalidArgumentException, PlatformException;

  /** @deprecated */
  public abstract TerminalCapabilities getTerminalCapabilities(Terminal paramTerminal)
    throws InvalidArgumentException, PlatformException;

  public abstract void addProviderListener(ProviderListener paramProviderListener)
    throws ResourceUnavailableException, MethodNotSupportedException;

  public abstract ProviderListener[] getProviderListeners();

  public abstract void removeProviderListener(ProviderListener paramProviderListener);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.Provider
 * JD-Core Version:    0.5.4
 */