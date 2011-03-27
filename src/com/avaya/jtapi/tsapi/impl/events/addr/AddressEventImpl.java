package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.AddressEvent;

import com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent;

public class AddressEventImpl extends TsapiListenerEvent implements
		AddressEvent {
	private final Address address;

	public AddressEventImpl(final AddressEventParams addressEventParams,
			final Address address) {
		super(addressEventParams.getEventId(), addressEventParams.getCause(),
				addressEventParams.getMetaEvent(), addressEventParams
						.getSource(), addressEventParams.getPrivateData());

		this.address = address;
	}

	@Override
	public Address getAddress() {
		return address;
	}
}
