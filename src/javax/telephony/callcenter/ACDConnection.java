package javax.telephony.callcenter;

import javax.telephony.Connection;
import javax.telephony.MethodNotSupportedException;

public abstract interface ACDConnection extends Connection {
	public abstract ACDManagerConnection getACDManagerConnection()
			throws MethodNotSupportedException;
}

