package com.avaya.jtapi.tsapi;

import javax.telephony.Connection;

public abstract interface LucentTrunk extends ITsapiTrunk
{
  public abstract Connection getConnection();

  public abstract String getGroupName();

  public abstract String getMemberName();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentTrunk
 * JD-Core Version:    0.5.4
 */