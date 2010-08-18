package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.TerminalConnectionEvent;
import javax.telephony.TerminalConnectionListener;

public class TerminalConnectionListenerAdapter extends
		ConnectionListenerAdapter implements TerminalConnectionListener {
	public void terminalConnectionActive(TerminalConnectionEvent event) {
	}

	public void terminalConnectionCreated(TerminalConnectionEvent event) {
	}

	public void terminalConnectionDropped(TerminalConnectionEvent event) {
	}

	public void terminalConnectionPassive(TerminalConnectionEvent event) {
	}

	public void terminalConnectionRinging(TerminalConnectionEvent event) {
	}

	public void terminalConnectionUnknown(TerminalConnectionEvent event) {
	}
}
