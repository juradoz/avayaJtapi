package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.privatedata.events.PrivateAddrEv;

@SuppressWarnings("deprecation")
public final class TsapiPrivateAddressEvent extends TsapiAddressEvent implements
		PrivateAddrEv {
	public TsapiPrivateAddressEvent(Address _address, int _cause,
			int _metaCode, Object _privateData) {
		super(_address, _cause, _metaCode, _privateData, 5);
	}

	public int getID() {
		return 600;
	}
}
