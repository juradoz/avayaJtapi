package javax.telephony;

import javax.telephony.capabilities.TerminalConnectionCapabilities;

public abstract interface TerminalConnection {
	public static final int IDLE = 64;
	public static final int RINGING = 65;
	public static final int PASSIVE = 66;
	public static final int ACTIVE = 67;
	public static final int DROPPED = 68;
	public static final int UNKNOWN = 69;

	public abstract void answer() throws PrivilegeViolationException,
			ResourceUnavailableException, MethodNotSupportedException,
			InvalidStateException;

	public abstract TerminalConnectionCapabilities getCapabilities();

	public abstract Connection getConnection();

	public abstract int getState();

	public abstract Terminal getTerminal();

	/** @deprecated */
	@Deprecated
	public abstract TerminalConnectionCapabilities getTerminalConnectionCapabilities(
			Terminal paramTerminal, Address paramAddress)
			throws InvalidArgumentException, PlatformException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.TerminalConnection JD-Core Version: 0.5.4
 */