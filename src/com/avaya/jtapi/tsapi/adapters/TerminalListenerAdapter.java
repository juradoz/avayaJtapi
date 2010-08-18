package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.TerminalEvent;
import javax.telephony.TerminalListener;

public abstract class TerminalListenerAdapter implements TerminalListener {
	public void terminalListenerEnded(TerminalEvent event) {
	}
}
