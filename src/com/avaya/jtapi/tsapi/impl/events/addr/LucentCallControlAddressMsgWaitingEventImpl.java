package com.avaya.jtapi.tsapi.impl.events.addr;

import javax.telephony.Address;

import com.avaya.jtapi.tsapi.LucentCallControlAddressMsgWaitingEvent;

public class LucentCallControlAddressMsgWaitingEventImpl extends
		CallControlAddressEventImpl implements
		LucentCallControlAddressMsgWaitingEvent {
	public LucentCallControlAddressMsgWaitingEventImpl(
			AddressEventParams addressEventParams, Address address) {
		super(addressEventParams, address);
	}

	public int getMessageWaitingBits() {
		return mwBits;
	}
}
