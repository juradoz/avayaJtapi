package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;

public abstract class TsapiCallCentAddrEv extends TsapiAddressEvent {
	public TsapiCallCentAddrEv(Address _device, int _cause, int _metaCode,
			Object _privateData) {
		super(_device, _cause, _metaCode, _privateData, 2);
	}
}