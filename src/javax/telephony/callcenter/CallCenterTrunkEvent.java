package javax.telephony.callcenter;

public abstract interface CallCenterTrunkEvent extends CallCenterCallEvent {
	public static final int CALL_CENTER_TRUNK_VALID_EVENT = 317;
	public static final int CALL_CENTER_TRUNK_INVALID_EVENT = 318;

	public abstract CallCenterTrunk getTrunk();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcenter.CallCenterTrunkEvent JD-Core Version: 0.5.4
 */