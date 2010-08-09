package javax.telephony;

import javax.telephony.capabilities.TerminalCapabilities;

public abstract interface Terminal {
	public abstract void addCallListener(CallListener paramCallListener)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract void addCallObserver(CallObserver paramCallObserver)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract void addObserver(TerminalObserver paramTerminalObserver)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract void addTerminalListener(
			TerminalListener paramTerminalListener)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract Address[] getAddresses();

	public abstract CallListener[] getCallListeners();

	public abstract CallObserver[] getCallObservers();

	public abstract TerminalCapabilities getCapabilities();

	public abstract String getName();

	public abstract TerminalObserver[] getObservers();

	public abstract Provider getProvider();

	/** @deprecated */
	@Deprecated
	public abstract TerminalCapabilities getTerminalCapabilities(
			Terminal paramTerminal, Address paramAddress)
			throws InvalidArgumentException, PlatformException;

	public abstract TerminalConnection[] getTerminalConnections();

	public abstract TerminalListener[] getTerminalListeners();

	public abstract void removeCallListener(CallListener paramCallListener);

	public abstract void removeCallObserver(CallObserver paramCallObserver);

	public abstract void removeObserver(TerminalObserver paramTerminalObserver);

	public abstract void removeTerminalListener(
			TerminalListener paramTerminalListener);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.Terminal JD-Core Version: 0.5.4
 */