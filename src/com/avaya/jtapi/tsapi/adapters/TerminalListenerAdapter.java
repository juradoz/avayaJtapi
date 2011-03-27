package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.TerminalEvent;
import javax.telephony.TerminalListener;

public abstract class TerminalListenerAdapter implements TerminalListener {
	@Override
	public void terminalListenerEnded(final TerminalEvent event) {
	}
}
