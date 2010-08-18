package javax.telephony;

public class InvalidPartyException extends Exception {
	private static final long serialVersionUID = 1L;
	private final int _type;
	public static final int ORIGINATING_PARTY = 0;
	public static final int DESTINATION_PARTY = 1;
	public static final int UNKNOWN_PARTY = 2;

	public InvalidPartyException(final int type) {
		_type = type;
	}

	public InvalidPartyException(final int type, final String s) {
		super(s);
		_type = type;
	}

	public int getType() {
		return _type;
	}
}
