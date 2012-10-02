package javax.telephony;

public class PrivilegeViolationException extends Exception {
	private static final long serialVersionUID = 1L;
	private int _type;
	public static final int ORIGINATOR_VIOLATION = 0;
	public static final int DESTINATION_VIOLATION = 1;
	public static final int UNKNOWN_VIOLATION = 2;

	public PrivilegeViolationException(int type) {
		this._type = type;
	}

	public PrivilegeViolationException(int type, String s) {
		super(s);
		this._type = type;
	}

	public int getType() {
		return this._type;
	}
}