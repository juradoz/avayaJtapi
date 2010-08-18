package javax.telephony;

public class InvalidStateException extends Exception {
	private static final long serialVersionUID = 1L;
	private final int _type;
	private final int _state;
	private final Object _object;
	public static final int PROVIDER_OBJECT = 0;
	public static final int CALL_OBJECT = 1;
	public static final int CONNECTION_OBJECT = 2;
	public static final int TERMINAL_OBJECT = 3;
	public static final int ADDRESS_OBJECT = 4;
	public static final int TERMINAL_CONNECTION_OBJECT = 5;

	public InvalidStateException(final Object object, final int type,
			final int state) {
		_type = type;
		_object = object;
		_state = state;
	}

	public InvalidStateException(final Object object, final int type,
			final int state, final String s) {
		super(s);
		_type = type;
		_object = object;
		_state = state;
	}

	public Object getObject() {
		return _object;
	}

	public int getObjectType() {
		return _type;
	}

	public int getState() {
		return _state;
	}
}
