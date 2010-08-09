package javax.telephony;

public abstract interface CallEvent extends Event {
	public static final int CALL_ACTIVE = 101;
	public static final int CALL_INVALID = 102;
	public static final int CALL_EVENT_TRANSMISSION_ENDED = 103;

	public abstract Call getCall();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.CallEvent JD-Core Version: 0.5.4
 */