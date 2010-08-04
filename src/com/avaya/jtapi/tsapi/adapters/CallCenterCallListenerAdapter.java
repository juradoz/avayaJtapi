package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcenter.CallCenterCallEvent;
import javax.telephony.callcenter.CallCenterCallListener;
import javax.telephony.callcenter.CallCenterTrunkEvent;

public class CallCenterCallListenerAdapter extends CallListenerAdapter
  implements CallCenterCallListener
{
  public void applicationData(CallCenterCallEvent event)
  {
  }

  public void trunkInvalid(CallCenterTrunkEvent event)
  {
  }

  public void trunkValid(CallCenterTrunkEvent event)
  {
  }
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.adapters.CallCenterCallListenerAdapter
 * JD-Core Version:    0.5.4
 */