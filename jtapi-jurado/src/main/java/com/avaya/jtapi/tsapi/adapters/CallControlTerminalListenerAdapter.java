package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlTerminalEvent;
import javax.telephony.callcontrol.CallControlTerminalListener;

public abstract class CallControlTerminalListenerAdapter extends
		TerminalListenerAdapter implements CallControlTerminalListener {
	public void terminalDoNotDisturb(CallControlTerminalEvent event) {
	}
}