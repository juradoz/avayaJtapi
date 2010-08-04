package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlAddressEvent;
import javax.telephony.callcontrol.CallControlAddressListener;

public abstract class CallControlAddressListenerAdapter extends AddressListenerAdapter
  implements CallControlAddressListener
{
  public void addressDoNotDisturb(CallControlAddressEvent event)
  {
  }

  public void addressForwarded(CallControlAddressEvent event)
  {
  }

  public void addressMessageWaiting(CallControlAddressEvent event)
  {
  }
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.adapters.CallControlAddressListenerAdapter
 * JD-Core Version:    0.5.4
 */