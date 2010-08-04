package javax.telephony.callcenter;

import javax.telephony.Connection;
import javax.telephony.MethodNotSupportedException;

public abstract interface ACDConnection extends Connection
{
  public abstract ACDManagerConnection getACDManagerConnection()
    throws MethodNotSupportedException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.ACDConnection
 * JD-Core Version:    0.5.4
 */