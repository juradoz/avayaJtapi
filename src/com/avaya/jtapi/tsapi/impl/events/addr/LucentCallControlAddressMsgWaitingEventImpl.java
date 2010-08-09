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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com
 * .avaya.jtapi.tsapi.impl.events.addr.LucentCallControlAddressMsgWaitingEventImpl
 * JD-Core Version: 0.5.4
 */