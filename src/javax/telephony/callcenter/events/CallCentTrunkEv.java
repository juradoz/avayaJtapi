package javax.telephony.callcenter.events;

import javax.telephony.callcenter.CallCenterTrunk;

/** @deprecated */
@Deprecated
public abstract interface CallCentTrunkEv extends CallCentCallEv {
	public abstract CallCenterTrunk getTrunk();
}

