package javax.telephony.callcontrol;

import javax.telephony.TerminalConnectionListener;

public abstract interface CallControlTerminalConnectionListener extends
		CallControlConnectionListener, TerminalConnectionListener {
	public abstract void terminalConnectionBridged(
			CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

	public abstract void terminalConnectionDropped(
			CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

	public abstract void terminalConnectionHeld(
			CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

	public abstract void terminalConnectionInUse(
			CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

	public abstract void terminalConnectionRinging(
			CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

	public abstract void terminalConnectionTalking(
			CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

	public abstract void terminalConnectionUnknown(
			CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);
}
