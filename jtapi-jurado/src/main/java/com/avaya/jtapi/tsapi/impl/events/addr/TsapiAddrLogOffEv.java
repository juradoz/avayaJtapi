package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.events.ACDAddrLoggedOffEv;

@SuppressWarnings("deprecation")
public final class TsapiAddrLogOffEv extends TsapiACDAddrEv implements
		ACDAddrLoggedOffEv {
	public int getID() {
		return 301;
	}

	public TsapiAddrLogOffEv(Address _device, Agent _agent, int _cause,
			int _metaCode, Object _privateData) {
		super(_device, _agent, _cause, _metaCode, _privateData);
	}
}