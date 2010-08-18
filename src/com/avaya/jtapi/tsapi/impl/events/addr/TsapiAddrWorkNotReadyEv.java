package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.events.ACDAddrWorkNotReadyEv;

@SuppressWarnings("deprecation")
public final class TsapiAddrWorkNotReadyEv extends TsapiACDAddrEv implements
		ACDAddrWorkNotReadyEv {
	public TsapiAddrWorkNotReadyEv(Address _device, Agent _agent, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _agent, _cause, _metaCode, _privateData);
	}

	public int getID() {
		return 306;
	}
}
