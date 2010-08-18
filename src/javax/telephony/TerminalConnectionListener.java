package javax.telephony;

public abstract interface TerminalConnectionListener extends ConnectionListener {
	public abstract void terminalConnectionActive(
			TerminalConnectionEvent paramTerminalConnectionEvent);

	public abstract void terminalConnectionCreated(
			TerminalConnectionEvent paramTerminalConnectionEvent);

	public abstract void terminalConnectionDropped(
			TerminalConnectionEvent paramTerminalConnectionEvent);

	public abstract void terminalConnectionPassive(
			TerminalConnectionEvent paramTerminalConnectionEvent);

	public abstract void terminalConnectionRinging(
			TerminalConnectionEvent paramTerminalConnectionEvent);

	public abstract void terminalConnectionUnknown(
			TerminalConnectionEvent paramTerminalConnectionEvent);
}
