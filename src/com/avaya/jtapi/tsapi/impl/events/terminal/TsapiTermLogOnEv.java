package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.events.AgentTermLoggedOnEv;

@SuppressWarnings("deprecation")
public final class TsapiTermLogOnEv extends TsapiAgentTermEv implements
		AgentTermLoggedOnEv {
	public TsapiTermLogOnEv(final Terminal _device, final Agent _agent,
			final int _cause, final int _metaCode, final Object _privateData) {
		super(_device, _agent, _cause, _metaCode, _privateData);
	}

	public int getID() {
		return 310;
	}
}
