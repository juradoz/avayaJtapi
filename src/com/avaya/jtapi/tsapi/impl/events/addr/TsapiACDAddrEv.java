package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.AgentTerminal;
import javax.telephony.callcenter.CallCenterTrunk;

public abstract class TsapiACDAddrEv extends TsapiCallCentAddrEv {
	Agent agent = null;

	public TsapiACDAddrEv(Address _device, Agent _agent, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		agent = _agent;
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

	public final AgentTerminal getAgentTerminal() {
		return agent.getAgentTerminal();
	}

	public final int getState() {
		return agent.getState();
	}

	public final CallCenterTrunk[] getTrunks() {
		return null;
	}
}

