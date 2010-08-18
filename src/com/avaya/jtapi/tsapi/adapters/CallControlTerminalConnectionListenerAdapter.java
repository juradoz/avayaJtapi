package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionListener;

public class CallControlTerminalConnectionListenerAdapter extends
		TerminalConnectionListenerAdapter implements
		CallControlTerminalConnectionListener {
	public void connectionAlerting(final CallControlConnectionEvent event) {
	}

	public void connectionDialing(final CallControlConnectionEvent event) {
	}

	public void connectionDisconnected(final CallControlConnectionEvent event) {
	}

	public void connectionEstablished(final CallControlConnectionEvent event) {
	}

	public void connectionFailed(final CallControlConnectionEvent event) {
	}

	public void connectionInitiated(final CallControlConnectionEvent event) {
	}

	public void connectionNetworkAlerting(final CallControlConnectionEvent event) {
	}

	public void connectionNetworkReached(final CallControlConnectionEvent event) {
	}

	public void connectionOffered(final CallControlConnectionEvent event) {
	}

	public void connectionQueued(final CallControlConnectionEvent event) {
	}

	public void connectionUnknown(final CallControlConnectionEvent event) {
	}

	public void terminalConnectionBridged(
			final CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionDropped(
			final CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionHeld(
			final CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionInUse(
			final CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionRinging(
			final CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionTalking(
			final CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionUnknown(
			final CallControlTerminalConnectionEvent event) {
	}
}
