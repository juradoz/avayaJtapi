package javax.telephony;

import java.util.EventListener;

public abstract interface TerminalListener extends EventListener {
	public abstract void terminalListenerEnded(TerminalEvent paramTerminalEvent);
}

