package javax.telephony;

import javax.telephony.events.AddrEv;

/** @deprecated */
public abstract interface AddressObserver
{
  public abstract void addressChangedEvent(AddrEv[] paramArrayOfAddrEv);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.AddressObserver
 * JD-Core Version:    0.5.4
 */