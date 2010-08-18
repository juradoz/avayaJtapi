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

