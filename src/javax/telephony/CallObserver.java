package javax.telephony;

import javax.telephony.events.CallEv;

/** @deprecated */
public abstract interface CallObserver
{
  public abstract void callChangedEvent(CallEv[] paramArrayOfCallEv);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.CallObserver
 * JD-Core Version:    0.5.4
 */