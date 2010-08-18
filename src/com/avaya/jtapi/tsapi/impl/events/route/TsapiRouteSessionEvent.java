package com.avaya.jtapi.tsapi.impl.events.route;

import javax.telephony.callcenter.RouteSession;
import javax.telephony.callcenter.events.RouteSessionEvent;

public abstract class TsapiRouteSessionEvent implements RouteSessionEvent {
	private RouteSession routeSession = null;

	public TsapiRouteSessionEvent(final RouteSession routeSession) {
		this.routeSession = routeSession;
	}

	public final RouteSession getRouteSession() {
		return routeSession;
	}
}
