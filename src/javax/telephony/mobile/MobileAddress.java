package javax.telephony.mobile;

import javax.telephony.Address;
import javax.telephony.MethodNotSupportedException;

public abstract interface MobileAddress extends Address {
	public abstract boolean getCallWaiting() throws MethodNotSupportedException;

	public abstract String getSubscriptionId();

	public abstract void setCallWaiting(boolean paramBoolean)
			throws MethodNotSupportedException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.mobile.MobileAddress JD-Core Version: 0.5.4
 */