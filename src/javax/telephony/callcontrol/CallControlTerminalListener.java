package javax.telephony.callcontrol;

import javax.telephony.TerminalListener;

public abstract interface CallControlTerminalListener extends TerminalListener {
	public abstract void terminalDoNotDisturb(
			CallControlTerminalEvent paramCallControlTerminalEvent);
}
