package com.avaya.jtapi.tsapi;

import javax.telephony.Address;

public abstract interface LucentRouteUsedEvent extends ITsapiRouteUsedEvent {
	public abstract Address getRouteUsedAddress();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentRouteUsedEvent JD-Core Version: 0.5.4
 */