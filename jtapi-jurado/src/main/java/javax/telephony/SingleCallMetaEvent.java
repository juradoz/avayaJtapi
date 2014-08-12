package javax.telephony;

public abstract interface SingleCallMetaEvent extends MetaEvent {
	public static final int SINGLECALL_META_PROGRESS_STARTED = 210;
	public static final int SINGLECALL_META_PROGRESS_ENDED = 211;
	public static final int SINGLECALL_META_SNAPSHOT_STARTED = 212;
	public static final int SINGLECALL_META_SNAPSHOT_ENDED = 213;

	public abstract Call getCall();
}