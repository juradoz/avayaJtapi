package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.callcontrol.CallControlTerminalEvent;

import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallControlEvent;

public class CallControlTerminalEventImpl extends TsapiListenerCallControlEvent
		implements CallControlTerminalEvent {
	private final Terminal terminal;
	private boolean state = false;

	public CallControlTerminalEventImpl(
			final TerminalEventParams terminalEventParams, final boolean state) {
		super(terminalEventParams.getEventId(), terminalEventParams.getCause(),
				terminalEventParams.getMetaEvent(), terminalEventParams
						.getSource(), terminalEventParams.getPrivateData());

		terminal = terminalEventParams.getTerminal();
		this.state = state;
	}

	public boolean getDoNotDisturbState() {
		return state;
	}

	public Terminal getTerminal() {
		return terminal;
	}
}
