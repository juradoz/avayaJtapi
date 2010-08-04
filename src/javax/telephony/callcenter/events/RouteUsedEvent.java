package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.Terminal;

public abstract interface RouteUsedEvent extends RouteSessionEvent
{
  public abstract Terminal getRouteUsed();

  public abstract Terminal getCallingTerminal();

  public abstract Address getCallingAddress();

  public abstract boolean getDomain();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.events.RouteUsedEvent
 * JD-Core Version:    0.5.4
 */