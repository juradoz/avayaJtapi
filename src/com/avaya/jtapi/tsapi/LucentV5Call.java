package com.avaya.jtapi.tsapi;

import javax.telephony.Connection;

public abstract interface LucentV5Call extends ITsapiCall, LucentCallEx2, LucentV5CallInfo
{
  public abstract Connection addParty(String paramString, boolean paramBoolean)
    throws TsapiInvalidStateException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException;

  public abstract void setBillRate(short paramShort, float paramFloat)
    throws TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentV5Call
 * JD-Core Version:    0.5.4
 */