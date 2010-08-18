package javax.telephony;

public abstract interface MultiCallMetaEvent extends MetaEvent {
	public static final int MULTICALL_META_MERGE_STARTED = 200;
	public static final int MULTICALL_META_MERGE_ENDED = 201;
	public static final int MULTICALL_META_TRANSFER_STARTED = 202;
	public static final int MULTICALL_META_TRANSFER_ENDED = 203;

	public abstract Call getNewCall();

	public abstract Call[] getOldCalls();
}

