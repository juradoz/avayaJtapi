package javax.telephony;

public abstract interface TerminalEvent extends Event {
	public static final int TERMINAL_EVENT_TRANSMISSION_ENDED = 121;

	public abstract Terminal getTerminal();
}