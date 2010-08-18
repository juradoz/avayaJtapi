package javax.telephony;

import javax.telephony.events.TermEv;

/** @deprecated */
@Deprecated
public abstract interface TerminalObserver {
	public abstract void terminalChangedEvent(TermEv[] paramArrayOfTermEv);
}

