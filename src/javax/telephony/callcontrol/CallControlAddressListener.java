package javax.telephony.callcontrol;

import javax.telephony.AddressListener;

public abstract interface CallControlAddressListener extends AddressListener
{
  public abstract void addressDoNotDisturb(CallControlAddressEvent paramCallControlAddressEvent);

  public abstract void addressForwarded(CallControlAddressEvent paramCallControlAddressEvent);

  public abstract void addressMessageWaiting(CallControlAddressEvent paramCallControlAddressEvent);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.CallControlAddressListener
 * JD-Core Version:    0.5.4
 */