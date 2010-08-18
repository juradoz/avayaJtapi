package javax.telephony.privatedata.events;

import javax.telephony.events.TermEv;

/** @deprecated */
@Deprecated
public abstract interface PrivateTermEv extends TermEv {
	public static final int ID = 603;

	public abstract Object getPrivateData();
}
