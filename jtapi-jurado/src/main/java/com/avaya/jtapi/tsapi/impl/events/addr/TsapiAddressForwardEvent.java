package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcontrol.CallControlForwarding;
import javax.telephony.callcontrol.events.CallCtlAddrForwardEv;

@SuppressWarnings("deprecation")
public final class TsapiAddressForwardEvent extends TsapiCallCtlAddressEvent
		implements CallCtlAddrForwardEv {
	CallControlForwarding[] forwarding = null;

	public CallControlForwarding[] getForwarding() {
		return this.forwarding;
	}

	public int getID() {
		return 201;
	}

	public TsapiAddressForwardEvent(Address _device,
			CallControlForwarding[] _forwarding, int _cause, int _metaCode,
			Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		this.forwarding = _forwarding;
	}
}