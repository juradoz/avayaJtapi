package javax.telephony.phone.events;

import javax.telephony.events.TermEv;
import javax.telephony.phone.Component;
import javax.telephony.phone.ComponentGroup;

/** @deprecated */
@Deprecated
public abstract interface PhoneTermEv extends PhoneEv, TermEv {
	public abstract Component getComponent();

	public abstract ComponentGroup getComponentGroup();
}

