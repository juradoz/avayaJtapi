package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.events.ACDAddrReadyEv;

@SuppressWarnings("deprecation")
public final class TsapiAddrReadyEv extends TsapiACDAddrEv implements
		ACDAddrReadyEv {
	public TsapiAddrReadyEv(final Address _device, final Agent _agent,
			final int _cause, final int _metaCode, final Object _privateData) {
		super(_device, _agent, _cause, _metaCode, _privateData);
	}

	public int getID() {
		return 304;
	}
}
