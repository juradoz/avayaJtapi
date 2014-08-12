package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.ConnectionCapabilities;

public abstract interface ACDConnectionCapabilities extends
		ConnectionCapabilities {
	public abstract boolean canGetACDManagerConnection();
}