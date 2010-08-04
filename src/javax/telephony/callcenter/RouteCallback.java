package javax.telephony.callcenter;

import javax.telephony.callcenter.events.ReRouteEvent;
import javax.telephony.callcenter.events.RouteCallbackEndedEvent;
import javax.telephony.callcenter.events.RouteEndEvent;
import javax.telephony.callcenter.events.RouteEvent;
import javax.telephony.callcenter.events.RouteUsedEvent;

public abstract interface RouteCallback
{
  public abstract void routeEvent(RouteEvent paramRouteEvent);

  public abstract void reRouteEvent(ReRouteEvent paramReRouteEvent);

  public abstract void routeUsedEvent(RouteUsedEvent paramRouteUsedEvent);

  public abstract void routeEndEvent(RouteEndEvent paramRouteEndEvent);

  public abstract void routeCallbackEndedEvent(RouteCallbackEndedEvent paramRouteCallbackEndedEvent);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.RouteCallback
 * JD-Core Version:    0.5.4
 */