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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcenter.CallCenterCallListener JD-Core Version: 0.5.4
 */