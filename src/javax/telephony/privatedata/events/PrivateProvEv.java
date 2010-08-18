package javax.telephony.privatedata.events;

import javax.telephony.events.ProvEv;

/** @deprecated */
@Deprecated
public abstract interface PrivateProvEv extends ProvEv {
	public static final int ID = 602;

	public abstract Object getPrivateData();
}

