package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionListener;

public class CallControlTerminalConnectionListenerAdapter extends
		TerminalConnectionListenerAdapter implements
		CallControlTerminalConnectionListener {
	public void connectionAlerting(CallControlConnectionEvent event) {
	}

	public void connectionDialing(CallControlConnectionEvent event) {
	}

	public void connectionDisconnected(CallControlConnectionEvent event) {
	}

	public void connectionEstablished(CallControlConnectionEvent event) {
	}

	public void connectionFailed(CallControlConnectionEvent event) {
	}

	public void connectionInitiated(CallControlConnectionEvent event) {
	}

	public void connectionNetworkAlerting(CallControlConnectionEvent event) {
	}

	public void connectionNetworkReached(CallControlConnectionEvent event) {
	}

	public void connectionOffered(CallControlConnectionEvent event) {
	}

	public void connectionQueued(CallControlConnectionEvent event) {
	}

	public void connectionUnknown(CallControlConnectionEvent event) {
	}

	public void terminalConnectionBridged(
			CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionDropped(
			CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionHeld(CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionInUse(CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionRinging(
			CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionTalking(
			CallControlTerminalConnectionEvent event) {
	}

	public void terminalConnectionUnknown(
			CallControlTerminalConnectionEvent event) {
	}
}

