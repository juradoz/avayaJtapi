package javax.telephony;

public class ResourceUnavailableException extends Exception {
	private static final long serialVersionUID = 1L;
	private final int _type;
	public static final int UNKNOWN = 0;
	public static final int ORIGINATOR_UNAVAILABLE = 1;
	public static final int OBSERVER_LIMIT_EXCEEDED = 2;
	public static final int TRUNK_LIMIT_EXCEEDED = 3;
	public static final int OUTSTANDING_METHOD_EXCEEDED = 4;
	public static final int UNSPECIFIED_LIMIT_EXCEEDED = 5;
	public static final int NO_DIALTONE = 6;
	public static final int USER_RESPONSE = 7;

	public ResourceUnavailableException(final int type) {
		_type = type;
	}

	public ResourceUnavailableException(final int type, final String s) {
		super(s);
		_type = type;
	}

	public int getType() {
		return _type;
	}
}
