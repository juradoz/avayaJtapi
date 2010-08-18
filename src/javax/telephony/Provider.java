package javax.telephony;

import javax.telephony.capabilities.AddressCapabilities;
import javax.telephony.capabilities.CallCapabilities;
import javax.telephony.capabilities.ConnectionCapabilities;
import javax.telephony.capabilities.ProviderCapabilities;
import javax.telephony.capabilities.TerminalCapabilities;
import javax.telephony.capabilities.TerminalConnectionCapabilities;

@SuppressWarnings("deprecation")
public abstract interface Provider {
	public static final int IN_SERVICE = 16;
	public static final int OUT_OF_SERVICE = 17;
	public static final int SHUTDOWN = 18;

	public abstract void addObserver(ProviderObserver paramProviderObserver)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract void addProviderListener(
			ProviderListener paramProviderListener)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract Call createCall() throws ResourceUnavailableException,
			InvalidStateException, PrivilegeViolationException,
			MethodNotSupportedException;

	public abstract Address getAddress(String paramString)
			throws InvalidArgumentException;

	public abstract AddressCapabilities getAddressCapabilities();

	/** @deprecated */
	@Deprecated
	public abstract AddressCapabilities getAddressCapabilities(
			Terminal paramTerminal) throws InvalidArgumentException,
			PlatformException;

	public abstract Address[] getAddresses()
			throws ResourceUnavailableException;

	public abstract CallCapabilities getCallCapabilities();

	/** @deprecated */
	@Deprecated
	public abstract CallCapabilities getCallCapabilities(
			Terminal paramTerminal, Address paramAddress)
			throws InvalidArgumentException, PlatformException;

	public abstract Call[] getCalls() throws ResourceUnavailableException;

	public abstract ProviderCapabilities getCapabilities();

	public abstract ConnectionCapabilities getConnectionCapabilities();

	/** @deprecated */
	@Deprecated
	public abstract ConnectionCapabilities getConnectionCapabilities(
			Terminal paramTerminal, Address paramAddress)
			throws InvalidArgumentException, PlatformException;

	public abstract String getName();

	public abstract ProviderObserver[] getObservers();

	public abstract ProviderCapabilities getProviderCapabilities();

	/** @deprecated */
	@Deprecated
	public abstract ProviderCapabilities getProviderCapabilities(
			Terminal paramTerminal) throws InvalidArgumentException,
			PlatformException;

	public abstract ProviderListener[] getProviderListeners();

	public abstract int getState();

	public abstract Terminal getTerminal(String paramString)
			throws InvalidArgumentException;

	public abstract TerminalCapabilities getTerminalCapabilities();

	/** @deprecated */
	@Deprecated
	public abstract TerminalCapabilities getTerminalCapabilities(
			Terminal paramTerminal) throws InvalidArgumentException,
			PlatformException;

	public abstract TerminalConnectionCapabilities getTerminalConnectionCapabilities();

	/** @deprecated */
	@Deprecated
	public abstract TerminalConnectionCapabilities getTerminalConnectionCapabilities(
			Terminal paramTerminal) throws InvalidArgumentException,
			PlatformException;

	public abstract Terminal[] getTerminals()
			throws ResourceUnavailableException;

	public abstract void removeObserver(ProviderObserver paramProviderObserver);

	public abstract void removeProviderListener(
			ProviderListener paramProviderListener);

	public abstract void shutdown();
}

