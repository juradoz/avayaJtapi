package javax.telephony.callcenter;

import javax.telephony.MethodNotSupportedException;
import javax.telephony.Provider;

public abstract interface CallCenterProvider extends Provider {
	public abstract RouteAddress[] getRouteableAddresses()
			throws MethodNotSupportedException;

	public abstract ACDAddress[] getACDAddresses()
			throws MethodNotSupportedException;

	public abstract ACDManagerAddress[] getACDManagerAddresses()
			throws MethodNotSupportedException;
}