package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.privatedata.PrivateDataEvent;
import javax.telephony.privatedata.PrivateDataTerminalListener;

public abstract class PrivateDataTerminalListenerAdapter extends
		TerminalListenerAdapter implements PrivateDataTerminalListener {
	public void terminalPrivateData(PrivateDataEvent event) {
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.adapters.PrivateDataTerminalListenerAdapter JD-Core
 * Version: 0.5.4
 */