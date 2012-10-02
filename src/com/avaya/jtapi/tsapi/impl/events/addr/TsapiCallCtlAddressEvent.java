package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;

public abstract class TsapiCallCtlAddressEvent extends TsapiAddressEvent {
	public TsapiCallCtlAddressEvent(Address _device, int _cause, int _metaCode,
			Object _privateData) {
		super(_device, _cause, _metaCode, _privateData, 1);
	}
}