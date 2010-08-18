package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcenter.ACDAddressEvent;
import javax.telephony.callcenter.Agent;

import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallCenterEvent;

public class ACDAddressEventImpl extends TsapiListenerCallCenterEvent implements
		ACDAddressEvent {
	Agent agent;
	Address address;

	public ACDAddressEventImpl(AddressEventParams addressEventParams,
			Address address, Agent agent, Object privateData) {
		super(addressEventParams.getEventId(), addressEventParams.getCause(),
				addressEventParams.getMetaEvent(), addressEventParams
						.getSource(), privateData);

		this.agent = agent;
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public Agent getAgent() {
		return agent;
	}
}
