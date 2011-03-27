package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcontrol.CallControlForwarding;
import javax.telephony.callcontrol.events.CallCtlAddrForwardEv;

@SuppressWarnings("deprecation")
public final class TsapiAddressForwardEvent extends TsapiCallCtlAddressEvent
		implements CallCtlAddrForwardEv {
	CallControlForwarding[] forwarding = null;

	public TsapiAddressForwardEvent(final Address _device,
			final CallControlForwarding[] _forwarding, final int _cause,
			final int _metaCode, final Object _privateData) {
		super(_device, _cause, _metaCode, _privateData);
		forwarding = _forwarding;
	}

	@Override
	public CallControlForwarding[] getForwarding() {
		return forwarding;
	}

	@Override
	public int getID() {
		return 201;
	}
}
