package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlTerminalEvent;
import javax.telephony.callcontrol.CallControlTerminalListener;

public abstract class CallControlTerminalListenerAdapter extends
		TerminalListenerAdapter implements CallControlTerminalListener {
	@Override
	public void terminalDoNotDisturb(final CallControlTerminalEvent event) {
	}
}
