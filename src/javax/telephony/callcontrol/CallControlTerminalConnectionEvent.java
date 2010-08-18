package javax.telephony.callcontrol;

import javax.telephony.TerminalConnectionEvent;

public abstract interface CallControlTerminalConnectionEvent extends
		CallControlCallEvent, TerminalConnectionEvent {
	public static final int CALLCTL_TERMINAL_CONNECTION_BRIDGED = 364;
	public static final int CALLCTL_TERMINAL_CONNECTION_DROPPED = 365;
	public static final int CALLCTL_TERMINAL_CONNECTION_HELD = 366;
	public static final int CALLCTL_TERMINAL_CONNECTION_IN_USE = 367;
	public static final int CALLCTL_TERMINAL_CONNECTION_RINGING = 368;
	public static final int CALLCTL_TERMINAL_CONNECTION_TALKING = 369;
	public static final int CALLCTL_TERMINAL_CONNECTION_UNKNOWN = 370;
}
