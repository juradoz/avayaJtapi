package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.events.ACDAddrNotReadyEv;

@SuppressWarnings("deprecation")
public final class TsapiAddrNotReadyEv extends TsapiACDAddrEv implements
		ACDAddrNotReadyEv {
	public int getID() {
		return 303;
	}

	public TsapiAddrNotReadyEv(Address _device, Agent _agent, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _agent, _cause, _metaCode, _privateData);
	}
}