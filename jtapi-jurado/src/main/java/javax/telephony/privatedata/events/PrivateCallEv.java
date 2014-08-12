package javax.telephony.privatedata.events;

import javax.telephony.events.CallEv;

/** @deprecated */
public abstract interface PrivateCallEv extends CallEv {
	public static final int ID = 601;

	public abstract Object getPrivateData();
}