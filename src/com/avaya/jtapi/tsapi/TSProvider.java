package com.avaya.jtapi.tsapi;

public abstract class TSProvider
{
  public abstract void initNewProvider();

  public abstract void finalizeOldProvider();

  public abstract int getLucentPDV();

  public abstract boolean isLucentV7();

  public abstract boolean isLucentV8();

  public abstract boolean heartbeatIsEnabled();

  public abstract void enableHeartbeat();

  public abstract void disableHeartbeat();

  public abstract void setHeartbeatInterval(short paramShort)
    throws TsapiInvalidArgumentException;

  public abstract void heartbeatTimeout();

  public abstract String getServerID();

  public abstract void shutdown();

  public abstract String toString();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TSProvider
 * JD-Core Version:    0.5.4
 */