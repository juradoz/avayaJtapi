package javax.telephony.callcenter;

import javax.telephony.callcenter.events.ReRouteEvent;
import javax.telephony.callcenter.events.RouteCallbackEndedEvent;
import javax.telephony.callcenter.events.RouteEndEvent;
import javax.telephony.callcenter.events.RouteEvent;
import javax.telephony.callcenter.events.RouteUsedEvent;

public abstract interface RouteCallback {
	public abstract void reRouteEvent(ReRouteEvent paramReRouteEvent);

	public abstract void routeCallbackEndedEvent(
			RouteCallbackEndedEvent paramRouteCallbackEndedEvent);

	public abstract void routeEndEvent(RouteEndEvent paramRouteEndEvent);

	public abstract void routeEvent(RouteEvent paramRouteEvent);

	public abstract void routeUsedEvent(RouteUsedEvent paramRouteUsedEvent);
}

