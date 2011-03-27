package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionListener;

public class CallControlTerminalConnectionListenerAdapter extends
		TerminalConnectionListenerAdapter implements
		CallControlTerminalConnectionListener {
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

	@Override
	public void terminalConnectionBridged(
			final CallControlTerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionDropped(
			final CallControlTerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionHeld(
			final CallControlTerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionInUse(
			final CallControlTerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionRinging(
			final CallControlTerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionTalking(
			final CallControlTerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionUnknown(
			final CallControlTerminalConnectionEvent event) {
	}
}
