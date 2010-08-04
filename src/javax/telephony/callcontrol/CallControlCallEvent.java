package javax.telephony.callcontrol;

import javax.telephony.Address;
import javax.telephony.CallEvent;
import javax.telephony.Terminal;

public abstract interface CallControlCallEvent extends CallControlEvent, CallEvent
{
  public abstract Address getCallingAddress();

  public abstract Terminal getCallingTerminal();

  public abstract Address getCalledAddress();

  public abstract Address getLastRedirectedAddress();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.CallControlCallEvent
 * JD-Core Version:    0.5.4
 */