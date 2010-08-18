package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.ConnectionCapabilities;

public abstract interface ACDManagerConnectionCapabilities extends
		ConnectionCapabilities {
	public abstract boolean canGetACDConnections();
}

