package javax.telephony;

public class InvalidStateException extends Exception {
	private static final long serialVersionUID = 1L;
	private int _type;
	private int _state;
	private Object _object;
	public static final int PROVIDER_OBJECT = 0;
	public static final int CALL_OBJECT = 1;
	public static final int CONNECTION_OBJECT = 2;
	public static final int TERMINAL_OBJECT = 3;
	public static final int ADDRESS_OBJECT = 4;
	public static final int TERMINAL_CONNECTION_OBJECT = 5;

	public InvalidStateException(Object object, int type, int state) {
		_type = type;
		_object = object;
		_state = state;
	}

	public InvalidStateException(Object object, int type, int state, String s) {
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

