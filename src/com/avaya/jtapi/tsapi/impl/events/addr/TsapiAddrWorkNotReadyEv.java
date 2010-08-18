package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.events.ACDAddrWorkNotReadyEv;

@SuppressWarnings("deprecation")
public final class TsapiAddrWorkNotReadyEv extends TsapiACDAddrEv implements
		ACDAddrWorkNotReadyEv {
	public TsapiAddrWorkNotReadyEv(final Address _device, final Agent _agent,
			final int _cause, final int _metaCode, final Object _privateData) {
		super(_device, _agent, _cause, _metaCode, _privateData);
	}

	public int getID() {
		return 306;
	}
}
