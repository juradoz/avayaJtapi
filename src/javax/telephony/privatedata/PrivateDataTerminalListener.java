package javax.telephony.privatedata;

import javax.telephony.TerminalListener;

public abstract interface PrivateDataTerminalListener extends TerminalListener {
	public abstract void terminalPrivateData(
			PrivateDataEvent paramPrivateDataEvent);
}
