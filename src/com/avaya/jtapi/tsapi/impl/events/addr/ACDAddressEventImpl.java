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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.addr.ACDAddressEventImpl JD-Core Version:
 * 0.5.4
 */