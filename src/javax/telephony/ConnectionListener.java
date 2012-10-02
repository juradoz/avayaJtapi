package javax.telephony;

public abstract interface ConnectionListener extends CallListener {
	public abstract void connectionAlerting(ConnectionEvent paramConnectionEvent);

	public abstract void connectionConnected(
			ConnectionEvent paramConnectionEvent);

	public abstract void connectionCreated(ConnectionEvent paramConnectionEvent);

	public abstract void connectionDisconnected(
			ConnectionEvent paramConnectionEvent);

	public abstract void connectionFailed(ConnectionEvent paramConnectionEvent);

	public abstract void connectionInProgress(
			ConnectionEvent paramConnectionEvent);

	public abstract void connectionUnknown(ConnectionEvent paramConnectionEvent);
}