package javax.telephony.mobile;

import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.ResourceUnavailableException;

public abstract interface NetworkSelection extends MobileProvider {
	public abstract String getCurrentSelectionMode()
			throws MethodNotSupportedException;

	public abstract MobileNetwork[] getForbiddenNetworks()
			throws MethodNotSupportedException, ResourceUnavailableException;

	public abstract MobileNetwork[] getPreferredNetworks()
			throws MethodNotSupportedException, ResourceUnavailableException;

	public abstract String[] getSelectionModes()
			throws MethodNotSupportedException;

	public abstract void setSelectionMode(String paramString)
			throws InvalidArgumentException, MethodNotSupportedException,
			InvalidStateException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.mobile.NetworkSelection JD-Core Version: 0.5.4
 */