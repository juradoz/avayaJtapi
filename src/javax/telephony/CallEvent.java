package javax.telephony;

public abstract interface CallEvent extends Event {
	public static final int CALL_ACTIVE = 101;
	public static final int CALL_INVALID = 102;
	public static final int CALL_EVENT_TRANSMISSION_ENDED = 103;

	public abstract Call getCall();
}

