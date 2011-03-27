package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.privatedata.events.PrivateAddrEv;

@SuppressWarnings("deprecation")
public final class TsapiPrivateAddressEvent extends TsapiAddressEvent implements
		PrivateAddrEv {
	public TsapiPrivateAddressEvent(final Address _address, final int _cause,
			final int _metaCode, final Object _privateData) {
		super(_address, _cause, _metaCode, _privateData, 5);
	}

	@Override
	public int getID() {
		return 600;
	}
}
