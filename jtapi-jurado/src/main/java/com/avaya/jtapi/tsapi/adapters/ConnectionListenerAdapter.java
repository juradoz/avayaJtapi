package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.ConnectionEvent;
import javax.telephony.ConnectionListener;

public class ConnectionListenerAdapter extends CallListenerAdapter implements
		ConnectionListener {
	public void connectionAlerting(ConnectionEvent event) {
	}

	public void connectionConnected(ConnectionEvent event) {
	}

	public void connectionCreated(ConnectionEvent event) {
	}

	public void connectionDisconnected(ConnectionEvent event) {
	}

	public void connectionFailed(ConnectionEvent event) {
	}

	public void connectionInProgress(ConnectionEvent event) {
	}

	public void connectionUnknown(ConnectionEvent event) {
	}
}