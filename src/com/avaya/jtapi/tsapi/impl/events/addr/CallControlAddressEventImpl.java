package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;
import javax.telephony.callcontrol.CallControlAddressEvent;
import javax.telephony.callcontrol.CallControlForwarding;

import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallControlEvent;

public class CallControlAddressEventImpl extends TsapiListenerCallControlEvent
		implements CallControlAddressEvent {
	CallControlForwarding[] callControlForwarding;
	boolean doNotDisturbState;
	int mwBits = 0;
	Address address;

	public CallControlAddressEventImpl(AddressEventParams addressEventParams,
			Address address) {
		super(addressEventParams.getEventId(), addressEventParams.getCause(),
				addressEventParams.getMetaEvent(), addressEventParams
						.getSource(), addressEventParams.getPrivateData());

		callControlForwarding = addressEventParams.getCallControlForwarding();
		mwBits = addressEventParams.getMwBits();
		doNotDisturbState = addressEventParams.isDoNotDisturbState();
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public boolean getDoNotDisturbState() {
		return doNotDisturbState;
	}

	public CallControlForwarding[] getForwarding() {
		return callControlForwarding;
	}

	public boolean getMessageWaitingState() {
		return mwBits != 0;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.addr.CallControlAddressEventImpl JD-Core
 * Version: 0.5.4
 */