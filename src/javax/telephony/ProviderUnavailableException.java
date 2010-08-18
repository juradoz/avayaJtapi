package javax.telephony;

public class ProviderUnavailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public static final int CAUSE_UNKNOWN = 160;
	public static final int CAUSE_NOT_IN_SERVICE = 161;
	public static final int CAUSE_INVALID_SERVICE = 162;
	public static final int CAUSE_INVALID_ARGUMENT = 163;
	private int _cause;

	public ProviderUnavailableException() {
		_cause = 160;
	}

	public ProviderUnavailableException(int cause) {
		_cause = cause;
	}

	public ProviderUnavailableException(int cause, String s) {
		super(s);

		_cause = cause;
	}

	public ProviderUnavailableException(String s) {
		super(s);

		_cause = 160;
	}

	public int getReason() {
		return _cause;
	}
}

