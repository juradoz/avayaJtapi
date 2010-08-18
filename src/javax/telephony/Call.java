package javax.telephony;

import javax.telephony.capabilities.CallCapabilities;

@SuppressWarnings("deprecation")
public abstract interface Call {
	public static final int IDLE = 32;
	public static final int ACTIVE = 33;
	public static final int INVALID = 34;

	public abstract void addCallListener(CallListener paramCallListener)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract void addObserver(CallObserver paramCallObserver)
			throws ResourceUnavailableException, MethodNotSupportedException;

	public abstract Connection[] connect(Terminal paramTerminal,
			Address paramAddress, String paramString)
			throws ResourceUnavailableException, PrivilegeViolationException,
			InvalidPartyException, InvalidArgumentException,
			InvalidStateException, MethodNotSupportedException;

	/** @deprecated */
	@Deprecated
	public abstract CallCapabilities getCallCapabilities(
			Terminal paramTerminal, Address paramAddress)
			throws InvalidArgumentException, PlatformException;

	public abstract CallListener[] getCallListeners();

	public abstract CallCapabilities getCapabilities(Terminal paramTerminal,
			Address paramAddress) throws InvalidArgumentException;

	public abstract Connection[] getConnections();

	public abstract CallObserver[] getObservers();

	public abstract Provider getProvider();

	public abstract int getState();

	public abstract void removeCallListener(CallListener paramCallListener);

	public abstract void removeObserver(CallObserver paramCallObserver);
}

