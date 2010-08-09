package javax.telephony.callcontrol.capabilities;

import javax.telephony.capabilities.ConnectionCapabilities;

public abstract interface CallControlConnectionCapabilities extends
		ConnectionCapabilities {
	public abstract boolean canAccept();

	public abstract boolean canAddToAddress();

	public abstract boolean canPark();

	public abstract boolean canRedirect();

	public abstract boolean canReject();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.capabilities.CallControlConnectionCapabilities
 * JD-Core Version: 0.5.4
 */