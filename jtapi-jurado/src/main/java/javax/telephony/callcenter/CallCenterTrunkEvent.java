package javax.telephony.callcenter;

public abstract interface CallCenterTrunkEvent extends CallCenterCallEvent {
	public static final int CALL_CENTER_TRUNK_VALID_EVENT = 317;
	public static final int CALL_CENTER_TRUNK_INVALID_EVENT = 318;

	public abstract CallCenterTrunk getTrunk();
}