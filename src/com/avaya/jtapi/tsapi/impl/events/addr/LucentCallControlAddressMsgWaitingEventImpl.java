package com.avaya.jtapi.tsapi.impl.events.addr;

import com.avaya.jtapi.tsapi.LucentCallControlAddressMsgWaitingEvent;
import javax.telephony.Address;

public class LucentCallControlAddressMsgWaitingEventImpl extends
		CallControlAddressEventImpl implements
		LucentCallControlAddressMsgWaitingEvent {
	public LucentCallControlAddressMsgWaitingEventImpl(
			AddressEventParams addressEventParams, Address address) {
		super(addressEventParams, address);
	}

	public int getMessageWaitingBits() {
		return this.mwBits;
	}
}