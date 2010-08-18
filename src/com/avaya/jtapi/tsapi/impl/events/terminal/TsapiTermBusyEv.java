package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.events.AgentTermBusyEv;

@SuppressWarnings("deprecation")
public final class TsapiTermBusyEv extends TsapiAgentTermEv implements
		AgentTermBusyEv {
	public TsapiTermBusyEv(Terminal _device, Agent _agent, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _agent, _cause, _metaCode, _privateData);
	}

	public int getID() {
		return 308;
	}
}

