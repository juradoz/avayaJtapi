package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.TerminalConnectionEvent;
import javax.telephony.TerminalConnectionListener;

public class TerminalConnectionListenerAdapter extends
		ConnectionListenerAdapter implements TerminalConnectionListener {
	@Override
	public void terminalConnectionActive(final TerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionCreated(final TerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionDropped(final TerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionPassive(final TerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionRinging(final TerminalConnectionEvent event) {
	}

	@Override
	public void terminalConnectionUnknown(final TerminalConnectionEvent event) {
	}
}
