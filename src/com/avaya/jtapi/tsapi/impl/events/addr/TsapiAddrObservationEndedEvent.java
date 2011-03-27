package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.events.AddrObservationEndedEv;

public final class TsapiAddrObservationEndedEvent extends TsapiAddressEvent
		implements AddrObservationEndedEv {
	public TsapiAddrObservationEndedEvent(final Address _address,
			final int _cause, final Object _privateData) {
		super(_address, _cause, 136, _privateData);
	}

	@Override
	public int getID() {
		return 100;
	}
}
