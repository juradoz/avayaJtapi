package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.events.AddrObservationEndedEv;

public final class TsapiAddrObservationEndedEvent extends TsapiAddressEvent
		implements AddrObservationEndedEv {
	public TsapiAddrObservationEndedEvent(Address _address, int _cause,
			Object _privateData) {
		super(_address, _cause, 136, _privateData);
	}

	public int getID() {
		return 100;
	}
}
