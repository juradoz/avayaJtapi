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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.TerminalConnectionListener JD-Core Version: 0.5.4
 */