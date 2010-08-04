package javax.telephony.callcenter;

import javax.telephony.MethodNotSupportedException;
import javax.telephony.Provider;

public abstract interface CallCenterProvider extends Provider
{
  public abstract RouteAddress[] getRouteableAddresses()
    throws MethodNotSupportedException;

  public abstract ACDAddress[] getACDAddresses()
    throws MethodNotSupportedException;

  public abstract ACDManagerAddress[] getACDManagerAddresses()
    throws MethodNotSupportedException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.CallCenterProvider
 * JD-Core Version:    0.5.4
 */