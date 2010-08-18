package javax.telephony.callcenter;

import javax.telephony.MethodNotSupportedException;
import javax.telephony.Provider;

public abstract interface CallCenterProvider extends Provider {
	public abstract ACDAddress[] getACDAddresses()
			throws MethodNotSupportedException;

	public abstract ACDManagerAddress[] getACDManagerAddresses()
			throws MethodNotSupportedException;

	public abstract RouteAddress[] getRouteableAddresses()
			throws MethodNotSupportedException;
}

