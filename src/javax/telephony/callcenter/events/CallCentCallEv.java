package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.events.CallEv;

/** @deprecated */
@Deprecated
public abstract interface CallCentCallEv extends CallCentEv, CallEv {
	public abstract Address getCalledAddress();

	public abstract Address getCallingAddress();

	public abstract Terminal getCallingTerminal();

	public abstract Address getLastRedirectedAddress();

	/** @deprecated */
	@Deprecated
	public abstract CallCenterTrunk[] getTrunks();
}
