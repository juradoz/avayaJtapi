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

	public CallControlAddressEventImpl(
			final AddressEventParams addressEventParams, final Address address) {
		super(addressEventParams.getEventId(), addressEventParams.getCause(),
				addressEventParams.getMetaEvent(), addressEventParams
						.getSource(), addressEventParams.getPrivateData());

		callControlForwarding = addressEventParams.getCallControlForwarding();
		mwBits = addressEventParams.getMwBits();
		doNotDisturbState = addressEventParams.isDoNotDisturbState();
		this.address = address;
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public boolean getDoNotDisturbState() {
		return doNotDisturbState;
	}

	@Override
	public CallControlForwarding[] getForwarding() {
		return callControlForwarding;
	}

	@Override
	public boolean getMessageWaitingState() {
		return mwBits != 0;
	}
}
