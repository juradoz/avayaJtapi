package javax.telephony;

public class PlatformException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PlatformException() {
	}

	public PlatformException(String s) {
		super(s);
	}
}