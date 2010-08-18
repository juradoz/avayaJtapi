package javax.telephony;

public class PlatformException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PlatformException() {
	}

	public PlatformException(final String s) {
		super(s);
	}
}
