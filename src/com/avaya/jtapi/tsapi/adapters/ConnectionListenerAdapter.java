package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.ConnectionEvent;
import javax.telephony.ConnectionListener;

public class ConnectionListenerAdapter extends CallListenerAdapter implements
		ConnectionListener {
	@Override
	public void connectionAlerting(final ConnectionEvent event) {
	}

	@Override
	public void connectionConnected(final ConnectionEvent event) {
	}

	@Override
	public void connectionCreated(final ConnectionEvent event) {
	}

	@Override
	public void connectionDisconnected(final ConnectionEvent event) {
	}

	@Override
	public void connectionFailed(final ConnectionEvent event) {
	}

	@Override
	public void connectionInProgress(final ConnectionEvent event) {
	}

	@Override
	public void connectionUnknown(final ConnectionEvent event) {
	}
}
