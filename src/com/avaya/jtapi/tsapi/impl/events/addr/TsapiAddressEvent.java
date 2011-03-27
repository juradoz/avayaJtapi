package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.privatedata.events.PrivateAddrEv;

import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;

@SuppressWarnings("deprecation")
public abstract class TsapiAddressEvent extends TsapiObserverEvent implements
		PrivateAddrEv {
	Address address = null;

	public TsapiAddressEvent(final Address _address, final int _cause,
			final int _metaCode, final Object _privateData) {
		this(_address, _cause, _metaCode, _privateData, 0);
	}

	TsapiAddressEvent(final Address _address, final int _cause,
			final int _metaCode, final Object _privateData,
			final int _eventPackage) {
		super(_cause, _metaCode, _privateData, _eventPackage);
		address = _address;
	}

	@Override
	public final Address getAddress() {
		return address;
	}
}
