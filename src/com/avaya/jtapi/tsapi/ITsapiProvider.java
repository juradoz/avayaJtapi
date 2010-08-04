package com.avaya.jtapi.tsapi;

import javax.telephony.Call;
import javax.telephony.Provider;
import javax.telephony.callcenter.CallCenterProvider;

public abstract interface ITsapiProvider extends Provider, CallCenterProvider
{
  public static final int TSAPI_OUT_OF_SERVICE = 0;
  public static final int TSAPI_INITIALIZING = 1;
  public static final int TSAPI_IN_SERVICE = 2;
  public static final int TSAPI_SHUTDOWN = 3;

  public abstract int getTsapiState();

  public abstract String getVendor();

  public abstract byte[] getVendorVersion();

  public abstract void updateAddresses();

  public abstract void setDebugPrinting(boolean paramBoolean);

  public abstract void setSessionTimeout(int paramInt);

  public abstract int getCurrentStateOfCallByForceQueryOnTelephonyServer(int paramInt);

  public abstract int getCurrentStateOfCallByForceQueryOnTelephonyServer(Call paramCall);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.ITsapiProvider
 * JD-Core Version:    0.5.4
 */