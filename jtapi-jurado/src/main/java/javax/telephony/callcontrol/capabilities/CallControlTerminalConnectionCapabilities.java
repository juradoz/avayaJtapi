package javax.telephony.callcontrol.capabilities;

import javax.telephony.capabilities.TerminalConnectionCapabilities;

public abstract interface CallControlTerminalConnectionCapabilities extends
		TerminalConnectionCapabilities {
	public abstract boolean canHold();

	public abstract boolean canUnhold();

	public abstract boolean canJoin();

	public abstract boolean canLeave();
}