package javax.telephony.callcenter;

import javax.telephony.MethodNotSupportedException;

public abstract interface ACDManagerAddress extends CallCenterAddress {
	public abstract ACDAddress[] getACDAddresses()
			throws MethodNotSupportedException;
}
