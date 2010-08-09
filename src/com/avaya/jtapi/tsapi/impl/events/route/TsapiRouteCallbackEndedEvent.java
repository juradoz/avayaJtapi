package com.avaya.jtapi.tsapi.impl.events.route;

import javax.telephony.callcenter.RouteAddress;
import javax.telephony.callcenter.events.RouteCallbackEndedEvent;

public final class TsapiRouteCallbackEndedEvent implements
		RouteCallbackEndedEvent {
	private RouteAddress routeAddress = null;

	public TsapiRouteCallbackEndedEvent(RouteAddress routeAddress) {
		this.routeAddress = routeAddress;
	}

	public RouteAddress getRouteAddress() {
		return routeAddress;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.route.TsapiRouteCallbackEndedEvent JD-Core
 * Version: 0.5.4
 */