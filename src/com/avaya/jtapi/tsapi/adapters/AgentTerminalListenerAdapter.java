package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcenter.AgentTerminalEvent;
import javax.telephony.callcenter.AgentTerminalListener;

public abstract class AgentTerminalListenerAdapter extends
		TerminalListenerAdapter implements AgentTerminalListener {
	@Override
	public void agentTerminalBusy(final AgentTerminalEvent event) {
	}

	@Override
	public void agentTerminalLoggedOff(final AgentTerminalEvent event) {
	}

	@Override
	public void agentTerminalLoggedOn(final AgentTerminalEvent event) {
	}

	@Override
	public void agentTerminalNotReady(final AgentTerminalEvent event) {
	}

	@Override
	public void agentTerminalReady(final AgentTerminalEvent event) {
	}

	@Override
	public void agentTerminalUnknown(final AgentTerminalEvent event) {
	}

	@Override
	public void agentTerminalWorkNotReady(final AgentTerminalEvent event) {
	}

	@Override
	public void agentTerminalWorkReady(final AgentTerminalEvent event) {
	}
}
