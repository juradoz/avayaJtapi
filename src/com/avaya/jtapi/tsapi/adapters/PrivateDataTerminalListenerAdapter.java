package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.privatedata.PrivateDataEvent;
import javax.telephony.privatedata.PrivateDataTerminalListener;

public abstract class PrivateDataTerminalListenerAdapter extends
		TerminalListenerAdapter implements PrivateDataTerminalListener {
	public void terminalPrivateData(PrivateDataEvent event) {
	}
}
