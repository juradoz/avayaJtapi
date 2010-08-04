package com.avaya.jtapi.tsapi;

public abstract interface LucentChargeError
{
  public static final short CE_NONE = 0;
  public static final short CE_NO_FINAL_CHARGE = 1;
  public static final short CE_LESS_FINAL_CHARGE = 2;
  public static final short CE_CHARGE_TOO_LARGE = 3;
  public static final short CE_NETWORK_BUSY = 4;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentChargeError
 * JD-Core Version:    0.5.4
 */