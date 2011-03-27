package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlConnectionEvent;
import javax.telephony.callcontrol.CallControlConnectionListener;

public class CallControlConnectionListenerAdapter extends
		ConnectionListenerAdapter implements CallControlConnectionListener {
	@Override
	public void connectionAlerting(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionDialing(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionDisconnected(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionEstablished(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionFailed(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionInitiated(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionNetworkAlerting(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionNetworkReached(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionOffered(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionQueued(final CallControlConnectionEvent event) {
	}

	@Override
	public void connectionUnknown(final CallControlConnectionEvent event) {
	}
}
