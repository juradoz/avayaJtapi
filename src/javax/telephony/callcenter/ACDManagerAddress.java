package javax.telephony.callcenter;

import javax.telephony.MethodNotSupportedException;

public abstract interface ACDManagerAddress extends CallCenterAddress {
	public abstract ACDAddress[] getACDAddresses()
			throws MethodNotSupportedException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcenter.ACDManagerAddress JD-Core Version: 0.5.4
 */