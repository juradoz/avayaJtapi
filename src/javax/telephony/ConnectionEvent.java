package javax.telephony;

public abstract interface ConnectionEvent extends CallEvent {
	public static final int CONNECTION_ALERTING = 104;
	public static final int CONNECTION_CONNECTED = 105;
	public static final int CONNECTION_CREATED = 106;
	public static final int CONNECTION_DISCONNECTED = 107;
	public static final int CONNECTION_FAILED = 108;
	public static final int CONNECTION_IN_PROGRESS = 109;
	public static final int CONNECTION_UNKNOWN = 110;

	public abstract Connection getConnection();
}

