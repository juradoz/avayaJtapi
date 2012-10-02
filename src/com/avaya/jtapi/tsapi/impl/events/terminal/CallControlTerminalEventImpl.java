package com.avaya.jtapi.tsapi.impl.events.terminal;

import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallControlEvent;
import javax.telephony.Terminal;
import javax.telephony.callcontrol.CallControlTerminalEvent;

public class CallControlTerminalEventImpl extends TsapiListenerCallControlEvent
		implements CallControlTerminalEvent {
	private Terminal terminal;
	private boolean state = false;

	public CallControlTerminalEventImpl(
			TerminalEventParams terminalEventParams, boolean state) {
		super(terminalEventParams.getEventId(), terminalEventParams.getCause(),
				terminalEventParams.getMetaEvent(), terminalEventParams
						.getSource(), terminalEventParams.getPrivateData());

		this.terminal = terminalEventParams.getTerminal();
		this.state = state;
	}

	public Terminal getTerminal() {
		return this.terminal;
	}

	public boolean getDoNotDisturbState() {
		return this.state;
	}
}