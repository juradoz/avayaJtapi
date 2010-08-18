package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;

public abstract class TsapiCallCentAddrEv extends TsapiAddressEvent {
	public TsapiCallCentAddrEv(final Address _device, final int _cause,
			final int _metaCode, final Object _privateData) {
		super(_device, _cause, _metaCode, _privateData, 2);
	}
}
