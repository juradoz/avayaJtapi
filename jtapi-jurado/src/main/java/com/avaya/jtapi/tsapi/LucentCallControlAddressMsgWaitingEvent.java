package com.avaya.jtapi.tsapi;

import javax.telephony.callcontrol.CallControlAddressEvent;

public abstract interface LucentCallControlAddressMsgWaitingEvent extends
		CallControlAddressEvent {
	public abstract int getMessageWaitingBits();
}