package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.ConnectionEvent;
import javax.telephony.ConnectionListener;

public class ConnectionListenerAdapter extends CallListenerAdapter implements
		ConnectionListener {
	public void connectionAlerting(final ConnectionEvent event) {
	}

	public void connectionConnected(final ConnectionEvent event) {
	}

	public void connectionCreated(final ConnectionEvent event) {
	}

	public void connectionDisconnected(final ConnectionEvent event) {
	}

	public void connectionFailed(final ConnectionEvent event) {
	}

	public void connectionInProgress(final ConnectionEvent event) {
	}

	public void connectionUnknown(final ConnectionEvent event) {
	}
}
