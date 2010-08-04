package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.privatedata.PrivateDataCallListener;
import javax.telephony.privatedata.PrivateDataEvent;

public class PrivateDataCallListenerAdapter extends CallListenerAdapter
  implements PrivateDataCallListener
{
  public void callPrivateData(PrivateDataEvent event)
  {
  }
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.adapters.PrivateDataCallListenerAdapter
 * JD-Core Version:    0.5.4
 */