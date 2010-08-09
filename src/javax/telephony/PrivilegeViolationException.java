package javax.telephony;

public class PrivilegeViolationException extends Exception {
	private static final long serialVersionUID = 1L;
	private int _type;
	public static final int ORIGINATOR_VIOLATION = 0;
	public static final int DESTINATION_VIOLATION = 1;
	public static final int UNKNOWN_VIOLATION = 2;

	public PrivilegeViolationException(int type) {
		_type = type;
	}

	public PrivilegeViolationException(int type, String s) {
		super(s);
		_type = type;
	}

	public int getType() {
		return _type;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.PrivilegeViolationException JD-Core Version: 0.5.4
 */