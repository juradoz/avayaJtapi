package javax.telephony.events;

import javax.telephony.Terminal;

public abstract interface TermEv extends Ev {
	public abstract Terminal getTerminal();
}
