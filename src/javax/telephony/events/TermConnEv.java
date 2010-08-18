package javax.telephony.events;

import javax.telephony.TerminalConnection;

public abstract interface TermConnEv extends CallEv {
	public abstract TerminalConnection getTerminalConnection();
}
