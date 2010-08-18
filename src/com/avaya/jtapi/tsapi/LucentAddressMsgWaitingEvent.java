package com.avaya.jtapi.tsapi;

public abstract interface LucentAddressMsgWaitingEvent extends
		ITsapiAddressMsgWaitingEvent {
	public abstract int getMessageWaitingBits();
}
