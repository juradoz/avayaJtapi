package javax.telephony.callcenter;

import javax.telephony.Connection;
import javax.telephony.MethodNotSupportedException;

public abstract interface ACDManagerConnection extends Connection {
	public abstract ACDConnection[] getACDConnections()
			throws MethodNotSupportedException;
}