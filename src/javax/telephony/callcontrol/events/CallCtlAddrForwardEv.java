package javax.telephony.callcontrol.events;

import javax.telephony.callcontrol.CallControlForwarding;

/** @deprecated */
@Deprecated
public abstract interface CallCtlAddrForwardEv extends CallCtlAddrEv {
	public static final int ID = 201;

	public abstract CallControlForwarding[] getForwarding();
}
