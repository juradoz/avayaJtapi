package javax.telephony.callcontrol;

import javax.telephony.ConnectionListener;

public abstract interface CallControlConnectionListener extends
		CallControlCallListener, ConnectionListener {
	public abstract void connectionAlerting(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionDialing(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionDisconnected(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionEstablished(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionFailed(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionInitiated(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionNetworkAlerting(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionNetworkReached(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionOffered(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionQueued(
			CallControlConnectionEvent paramCallControlConnectionEvent);

	public abstract void connectionUnknown(
			CallControlConnectionEvent paramCallControlConnectionEvent);
}

