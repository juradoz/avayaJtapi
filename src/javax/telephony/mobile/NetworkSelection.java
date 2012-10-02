package javax.telephony.mobile;

import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.ResourceUnavailableException;

public abstract interface NetworkSelection extends MobileProvider {
	public abstract String getCurrentSelectionMode()
			throws MethodNotSupportedException;

	public abstract String[] getSelectionModes()
			throws MethodNotSupportedException;

	public abstract void setSelectionMode(String paramString)
			throws InvalidArgumentException, MethodNotSupportedException,
			InvalidStateException;

	public abstract MobileNetwork[] getPreferredNetworks()
			throws MethodNotSupportedException, ResourceUnavailableException;

	public abstract MobileNetwork[] getForbiddenNetworks()
			throws MethodNotSupportedException, ResourceUnavailableException;
}