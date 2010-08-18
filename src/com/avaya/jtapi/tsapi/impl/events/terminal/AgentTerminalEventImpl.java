package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.AgentTerminalEvent;

import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallCenterEvent;

public class AgentTerminalEventImpl extends TsapiListenerCallCenterEvent
		implements AgentTerminalEvent {
	private final Terminal terminal;
	private final Agent agent;

	public AgentTerminalEventImpl(
			final TerminalEventParams terminalEventParams, final Agent agent) {
		super(terminalEventParams.getEventId(), terminalEventParams.getCause(),
				terminalEventParams.getMetaEvent(), terminalEventParams
						.getSource(), terminalEventParams.getPrivateData());

		terminal = terminalEventParams.getTerminal();
		this.agent = agent;
	}

	public Agent getAgent() {
		return agent;
	}

	public Terminal getTerminal() {
		return terminal;
	}
}
