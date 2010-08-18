package javax.telephony.phone;

import javax.telephony.phone.capabilities.ComponentCapabilities;

public abstract interface Component {
	public abstract ComponentCapabilities getCapabilities();

	public abstract String getName();
}
