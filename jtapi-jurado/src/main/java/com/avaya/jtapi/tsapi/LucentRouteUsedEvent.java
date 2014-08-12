package com.avaya.jtapi.tsapi;

import javax.telephony.Address;

public abstract interface LucentRouteUsedEvent extends ITsapiRouteUsedEvent {
	public abstract Address getRouteUsedAddress();
}