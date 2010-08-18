package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcenter.AgentTerminalEvent;
import javax.telephony.callcenter.AgentTerminalListener;

public abstract class AgentTerminalListenerAdapter extends
		TerminalListenerAdapter implements AgentTerminalListener {
	public void agentTerminalBusy(final AgentTerminalEvent event) {
	}

	public void agentTerminalLoggedOff(final AgentTerminalEvent event) {
	}

	public void agentTerminalLoggedOn(final AgentTerminalEvent event) {
	}

	public void agentTerminalNotReady(final AgentTerminalEvent event) {
	}

	public void agentTerminalReady(final AgentTerminalEvent event) {
	}

	public void agentTerminalUnknown(final AgentTerminalEvent event) {
	}

	public void agentTerminalWorkNotReady(final AgentTerminalEvent event) {
	}

	public void agentTerminalWorkReady(final AgentTerminalEvent event) {
	}
}
