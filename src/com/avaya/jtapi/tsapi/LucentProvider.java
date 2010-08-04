package com.avaya.jtapi.tsapi;

import java.util.Date;

public abstract interface LucentProvider extends ITsapiProviderEx2
{
  public abstract TrunkGroupInfo getTrunkGroupInfo(String paramString)
    throws TsapiMethodNotSupportedException;

  public abstract CallClassifierInfo getCallClassifierInfo()
    throws TsapiMethodNotSupportedException;

  public abstract Date getSwitchDateAndTime()
    throws TsapiMethodNotSupportedException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentProvider
 * JD-Core Version:    0.5.4
 */