package javax.telephony.privatedata.events;

import javax.telephony.events.TermEv;

/** @deprecated */
public abstract interface PrivateTermEv extends TermEv
{
  public static final int ID = 603;

  public abstract Object getPrivateData();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.privatedata.events.PrivateTermEv
 * JD-Core Version:    0.5.4
 */