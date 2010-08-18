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
