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

