package javax.telephony.callcenter;

import javax.telephony.Connection;
import javax.telephony.MethodNotSupportedException;

public abstract interface ACDManagerConnection extends Connection {
	public abstract ACDConnection[] getACDConnections()
			throws MethodNotSupportedException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcenter.ACDManagerConnection JD-Core Version: 0.5.4
 */