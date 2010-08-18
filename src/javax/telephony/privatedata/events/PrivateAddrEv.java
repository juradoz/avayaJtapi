package javax.telephony.privatedata.events;

import javax.telephony.events.AddrEv;

/** @deprecated */
@Deprecated
public abstract interface PrivateAddrEv extends AddrEv {
	public static final int ID = 600;

	public abstract Object getPrivateData();
}
