package javax.telephony.callcenter;

import javax.telephony.Address;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.ResourceUnavailableException;

public abstract interface RouteAddress extends Address {
	public static final String ALL_ROUTE_ADDRESS = "AllRouteAddress";

	public abstract void cancelRouteCallback(RouteCallback paramRouteCallback)
			throws MethodNotSupportedException;

	public abstract RouteSession[] getActiveRouteSessions()
			throws MethodNotSupportedException;

	public abstract RouteCallback[] getRouteCallback()
			throws MethodNotSupportedException;

	public abstract void registerRouteCallback(RouteCallback paramRouteCallback)
			throws ResourceUnavailableException, MethodNotSupportedException;
}

