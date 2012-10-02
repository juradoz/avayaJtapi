package javax.telephony.callcenter;

import javax.telephony.TerminalEvent;

public abstract interface AgentTerminalEvent extends CallCenterEvent,
		TerminalEvent {
	public static final int AGENT_TERMINAL_BUSY = 308;
	public static final int AGENT_TERMINAL_LOGGED_OFF = 309;
	public static final int AGENT_TERMINAL_LOGGED_ON = 310;
	public static final int AGENT_TERMINAL_NOT_READY = 311;
	public static final int AGENT_TERMINAL_READY = 312;
	public static final int AGENT_TERMINAL_UNKNOWN = 313;
	public static final int AGENT_TERMINAL_WORK_NOT_READY = 314;
	public static final int AGENT_TERMINAL_WORK_READY = 315;

	public abstract Agent getAgent();
}