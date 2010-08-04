package com.avaya.jtapi.tsapi;

import javax.telephony.callcontrol.CallControlAddressEvent;

public abstract interface LucentCallControlAddressMsgWaitingEvent extends CallControlAddressEvent
{
  public abstract int getMessageWaitingBits();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentCallControlAddressMsgWaitingEvent
 * JD-Core Version:    0.5.4
 */