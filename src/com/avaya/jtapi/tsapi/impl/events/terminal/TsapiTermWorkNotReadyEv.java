package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.events.AgentTermWorkNotReadyEv;

@SuppressWarnings("deprecation")
public final class TsapiTermWorkNotReadyEv extends TsapiAgentTermEv implements
		AgentTermWorkNotReadyEv {
	public TsapiTermWorkNotReadyEv(Terminal _device, Agent _agent, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _agent, _cause, _metaCode, _privateData);
	}

	public int getID() {
		return 314;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermWorkNotReadyEv JD-Core
 * Version: 0.5.4
 */