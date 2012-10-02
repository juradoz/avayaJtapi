package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.CallCapabilities;

public abstract interface CallCenterCallCapabilities extends CallCapabilities {
	public abstract boolean canConnectPredictive();

	public abstract boolean canHandleApplicationData();

	public abstract boolean canGetTrunks();
}