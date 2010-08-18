package javax.telephony.callcenter;

import javax.telephony.MethodNotSupportedException;

public abstract interface RouteSession {
	public static final int ROUTE = 1;
	public static final int ROUTE_USED = 2;
	public static final int ROUTE_END = 3;
	public static final int RE_ROUTE = 4;
	public static final int ROUTE_CALLBACK_ENDED = 5;
	public static final int CAUSE_NO_ERROR = 100;
	public static final int CAUSE_ROUTING_TIMER_EXPIRED = 101;
	public static final int CAUSE_PARAMETER_NOT_SUPPORTED = 102;
	public static final int CAUSE_INVALID_DESTINATION = 103;
	public static final int CAUSE_STATE_INCOMPATIBLE = 104;
	public static final int CAUSE_UNSPECIFIED_ERROR = 105;
	public static final int ERROR_UNKNOWN = 1;
	public static final int ERROR_RESOURCE_BUSY = 2;
	public static final int ERROR_RESOURCE_OUT_OF_SERVICE = 3;

	public abstract void endRoute(int paramInt)
			throws MethodNotSupportedException;

	public abstract int getCause();

	public abstract RouteAddress getRouteAddress();

	public abstract int getState();

	public abstract void selectRoute(String[] paramArrayOfString)
			throws MethodNotSupportedException;
}

