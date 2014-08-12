package javax.telephony.phone;

import javax.telephony.phone.capabilities.ComponentCapabilities;

public abstract interface Component {
	public abstract String getName();

	public abstract ComponentCapabilities getCapabilities();
}