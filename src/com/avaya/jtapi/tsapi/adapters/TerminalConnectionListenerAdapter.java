package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.TerminalConnectionEvent;
import javax.telephony.TerminalConnectionListener;

public class TerminalConnectionListenerAdapter extends
		ConnectionListenerAdapter implements TerminalConnectionListener {
	public void terminalConnectionActive(final TerminalConnectionEvent event) {
	}

	public void terminalConnectionCreated(final TerminalConnectionEvent event) {
	}

	public void terminalConnectionDropped(final TerminalConnectionEvent event) {
	}

	public void terminalConnectionPassive(final TerminalConnectionEvent event) {
	}

	public void terminalConnectionRinging(final TerminalConnectionEvent event) {
	}

	public void terminalConnectionUnknown(final TerminalConnectionEvent event) {
	}
}
