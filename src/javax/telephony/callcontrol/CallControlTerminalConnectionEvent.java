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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.CallControlTerminalConnectionEvent JD-Core
 * Version: 0.5.4
 */