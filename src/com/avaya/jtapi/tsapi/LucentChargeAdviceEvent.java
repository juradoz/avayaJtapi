package com.avaya.jtapi.tsapi;

public abstract interface LucentChargeAdviceEvent
{
  public abstract LucentCall getCall();

  public abstract LucentAddress getCalledAddress();

  public abstract LucentAddress getChargingAddress();

  public abstract TsapiTrunk getTrunk();

  public abstract int getCharge();

  public abstract short getChargeType();

  public abstract short getChargeError();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentChargeAdviceEvent
 * JD-Core Version:    0.5.4
 */