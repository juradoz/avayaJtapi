package javax.telephony.callcenter.events;

import javax.telephony.callcenter.RouteAddress;

public abstract interface RouteCallbackEndedEvent {
	public abstract RouteAddress getRouteAddress();
}

