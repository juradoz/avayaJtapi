package javax.telephony;

public abstract interface TerminalConnectionEvent extends CallEvent {
	public static final int TERMINAL_CONNECTION_ACTIVE = 115;
	public static final int TERMINAL_CONNECTION_CREATED = 116;
	public static final int TERMINAL_CONNECTION_DROPPED = 117;
	public static final int TERMINAL_CONNECTION_PASSIVE = 118;
	public static final int TERMINAL_CONNECTION_RINGING = 119;
	public static final int TERMINAL_CONNECTION_UNKNOWN = 120;

	public abstract TerminalConnection getTerminalConnection();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.TerminalConnectionEvent JD-Core Version: 0.5.4
 */