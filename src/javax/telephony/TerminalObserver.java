package javax.telephony;

import javax.telephony.events.TermEv;

/** @deprecated */
public abstract interface TerminalObserver
{
  public abstract void terminalChangedEvent(TermEv[] paramArrayOfTermEv);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.TerminalObserver
 * JD-Core Version:    0.5.4
 */