package javax.telephony.callcontrol;

import javax.telephony.TerminalEvent;

public abstract interface CallControlTerminalEvent extends CallControlEvent,
		TerminalEvent {
	public static final int CALLCTL_TERMINAL_EVENT_DO_NOT_DISTURB = 371;

	public abstract boolean getDoNotDisturbState();
}