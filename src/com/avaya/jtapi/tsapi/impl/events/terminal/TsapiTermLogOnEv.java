package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.events.AgentTermLoggedOnEv;

@SuppressWarnings("deprecation")
public final class TsapiTermLogOnEv extends TsapiAgentTermEv implements
		AgentTermLoggedOnEv {
	public int getID() {
		return 310;
	}

	public TsapiTermLogOnEv(Terminal _device, Agent _agent, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _agent, _cause, _metaCode, _privateData);
	}
}