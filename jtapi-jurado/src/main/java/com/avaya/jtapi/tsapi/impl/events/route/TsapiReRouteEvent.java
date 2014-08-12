package com.avaya.jtapi.tsapi.impl.events.route;

import javax.telephony.callcenter.RouteSession;
import javax.telephony.callcenter.events.ReRouteEvent;

public final class TsapiReRouteEvent extends TsapiRouteSessionEvent implements
		ReRouteEvent {
	public TsapiReRouteEvent(RouteSession routeSession) {
		super(routeSession);
	}
}