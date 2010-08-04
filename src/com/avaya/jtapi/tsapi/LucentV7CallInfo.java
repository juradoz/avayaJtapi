package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.CallCenterAddress;

public abstract interface LucentV7CallInfo extends LucentV5CallInfo
{
  public abstract V7DeviceHistoryEntry[] getDeviceHistory();

  public abstract CallCenterAddress getDistributingVDNAddress();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentV7CallInfo
 * JD-Core Version:    0.5.4
 */