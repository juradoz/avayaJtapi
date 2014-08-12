package javax.telephony;

import javax.telephony.events.TermEv;

/** @deprecated */
public abstract interface TerminalObserver {
	public abstract void terminalChangedEvent(TermEv[] paramArrayOfTermEv);
}