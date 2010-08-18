package javax.telephony.mobile;

import java.util.Date;
import java.util.TimeZone;

import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.Provider;
import javax.telephony.ResourceUnavailableException;

public abstract interface MobileProvider extends Provider {
	public static final int RESTRICTED_SERVICE = 19;

	public abstract void cancelAvailableNetworkRequest()
			throws MethodNotSupportedException, InvalidStateException;

	public abstract MobileNetwork[] getAvailableNetworks(boolean paramBoolean)
			throws MethodNotSupportedException, InvalidStateException,
			ResourceUnavailableException;

	public abstract MobileNetwork getCurrentNetwork();

	public abstract MobileNetwork getHomeNetwork()
			throws MethodNotSupportedException, ResourceUnavailableException;

	public abstract int getMobileState();

	public abstract Date getNetworkTime() throws MethodNotSupportedException;

	public abstract TimeZone getNetworkTimeZone()
			throws MethodNotSupportedException;

	public abstract String getType();

	public abstract boolean isRoaming() throws MethodNotSupportedException,
			InvalidStateException;

	public abstract void setNetwork(MobileNetwork paramMobileNetwork)
			throws MethodNotSupportedException, InvalidStateException,
			ResourceUnavailableException;
}
