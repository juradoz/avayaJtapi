package javax.telephony.mobile;

import javax.telephony.Address;
import javax.telephony.MethodNotSupportedException;

public abstract interface MobileAddress extends Address {
	public abstract boolean getCallWaiting() throws MethodNotSupportedException;

	public abstract String getSubscriptionId();

	public abstract void setCallWaiting(boolean paramBoolean)
			throws MethodNotSupportedException;
}
