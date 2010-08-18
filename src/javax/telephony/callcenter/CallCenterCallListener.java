package javax.telephony.callcenter;

import javax.telephony.CallListener;

public abstract interface CallCenterCallListener extends CallListener {
	public abstract void applicationData(
			CallCenterCallEvent paramCallCenterCallEvent);

	public abstract void trunkInvalid(
			CallCenterTrunkEvent paramCallCenterTrunkEvent);

	public abstract void trunkValid(
			CallCenterTrunkEvent paramCallCenterTrunkEvent);
}

