package javax.telephony;

import javax.telephony.capabilities.CallCapabilities;

@SuppressWarnings("deprecation")
public abstract interface Call {
	public static final int IDLE = 32;
	public static final int ACTIVE = 33;
	public static final int INVALID = 34;

	public abstract Connection[] getConnections();

	public abstract Provider getProvider();

	public abstract int getState();

	public abstract Connection[] connect(Terminal paramTerminal,
			Address paramAddress, String paramString)
			throws ResourceUnavailableException, PrivilegeViolationException,
			InvalidPartyException, InvalidArgumentException,
			InvalidStateException, MethodNotSupportedException;

	public abstract void addObserver(CallObserver paramCallObserver)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract CallObserver[] getObservers();

	public abstract void removeObserver(CallObserver paramCallObserver);

	public abstract CallCapabilities getCapabilities(Terminal paramTerminal,
			Address paramAddress) throws InvalidArgumentException;

	/** @deprecated */
	public abstract CallCapabilities getCallCapabilities(
			Terminal paramTerminal, Address paramAddress)
			throws InvalidArgumentException, PlatformException;

	public abstract void addCallListener(CallListener paramCallListener)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract CallListener[] getCallListeners();

	public abstract void removeCallListener(CallListener paramCallListener);
}