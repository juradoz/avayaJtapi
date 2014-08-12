package javax.telephony.phone;

import javax.telephony.Terminal;

public abstract interface PhoneTerminal extends Terminal {
	public abstract ComponentGroup[] getComponentGroups();
}