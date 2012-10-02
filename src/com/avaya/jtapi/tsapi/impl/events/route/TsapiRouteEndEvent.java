package com.avaya.jtapi.tsapi.impl.events.route;

import javax.telephony.callcenter.RouteSession;
import javax.telephony.callcenter.events.RouteEndEvent;

public final class TsapiRouteEndEvent extends TsapiRouteSessionEvent implements
		RouteEndEvent {
	public TsapiRouteEndEvent(RouteSession routeSession) {
		super(routeSession);
	}
}