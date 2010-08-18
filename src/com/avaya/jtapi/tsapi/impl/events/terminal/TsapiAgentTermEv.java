package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.Agent;

public abstract class TsapiAgentTermEv extends TsapiCallCentTermEv {
	public Agent agent = null;

	public TsapiAgentTermEv(final Terminal _device, final Agent _agent,
			final int _cause, final int _metaCode, final Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		agent = _agent;
	}

	public final ACDAddress getACDAddress() {
		return agent.getACDAddress();
	}

	public final Agent getAgent() {
		return agent;
	}

	public final Address getAgentAddress() {
		return agent.getAgentAddress();
	}

	public final String getAgentID() {
		return agent.getAgentID();
	}

	public final int getState() {
		return agent.getState();
	}
}
