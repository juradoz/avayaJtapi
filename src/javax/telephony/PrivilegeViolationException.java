package javax.telephony;

public class PrivilegeViolationException extends Exception {
	private static final long serialVersionUID = 1L;
	private final int _type;
	public static final int ORIGINATOR_VIOLATION = 0;
	public static final int DESTINATION_VIOLATION = 1;
	public static final int UNKNOWN_VIOLATION = 2;

	public PrivilegeViolationException(final int type) {
		_type = type;
	}

	public PrivilegeViolationException(final int type, final String s) {
		super(s);
		_type = type;
	}

	public int getType() {
		return _type;
	}
}
