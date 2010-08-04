package javax.telephony.callcenter;

import javax.telephony.Address;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.ResourceUnavailableException;

public abstract interface RouteAddress extends Address
{
  public static final String ALL_ROUTE_ADDRESS = "AllRouteAddress";

  public abstract void registerRouteCallback(RouteCallback paramRouteCallback)
    throws ResourceUnavailableException, MethodNotSupportedException;

  public abstract void cancelRouteCallback(RouteCallback paramRouteCallback)
    throws MethodNotSupportedException;

  public abstract RouteCallback[] getRouteCallback()
    throws MethodNotSupportedException;

  public abstract RouteSession[] getActiveRouteSessions()
    throws MethodNotSupportedException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.RouteAddress
 * JD-Core Version:    0.5.4
 */