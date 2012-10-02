package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.Terminal;

public abstract interface RouteUsedEvent extends RouteSessionEvent {
	public abstract Terminal getRouteUsed();

	public abstract Terminal getCallingTerminal();

	public abstract Address getCallingAddress();

	public abstract boolean getDomain();
}