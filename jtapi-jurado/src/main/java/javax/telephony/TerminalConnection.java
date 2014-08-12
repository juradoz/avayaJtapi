package javax.telephony;

import javax.telephony.capabilities.TerminalConnectionCapabilities;

public abstract interface TerminalConnection {
	public static final int IDLE = 64;
	public static final int RINGING = 65;
	public static final int PASSIVE = 66;
	public static final int ACTIVE = 67;
	public static final int DROPPED = 68;
	public static final int UNKNOWN = 69;

	public abstract int getState();

	public abstract Terminal getTerminal();

	public abstract Connection getConnection();

	public abstract void answer() throws PrivilegeViolationException,
			ResourceUnavailableException, MethodNotSupportedException,
			InvalidStateException;

	public abstract TerminalConnectionCapabilities getCapabilities();

	/** @deprecated */
	public abstract TerminalConnectionCapabilities getTerminalConnectionCapabilities(
			Terminal paramTerminal, Address paramAddress)
			throws InvalidArgumentException, PlatformException;
}