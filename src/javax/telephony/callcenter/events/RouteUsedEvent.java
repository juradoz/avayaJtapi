package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.Terminal;

public abstract interface RouteUsedEvent extends RouteSessionEvent {
	public abstract Address getCallingAddress();

	public abstract Terminal getCallingTerminal();

	public abstract boolean getDomain();

	public abstract Terminal getRouteUsed();
}

