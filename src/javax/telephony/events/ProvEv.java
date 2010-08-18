package javax.telephony.events;

import javax.telephony.Provider;

public abstract interface ProvEv extends Ev {
	public abstract Provider getProvider();
}
