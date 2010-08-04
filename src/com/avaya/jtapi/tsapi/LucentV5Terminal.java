package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.Agent;

public abstract interface LucentV5Terminal extends LucentTerminal
{
  public abstract Agent addAgent(LucentAddress paramLucentAddress, ACDAddress paramACDAddress, int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2)
    throws TsapiInvalidArgumentException, TsapiInvalidStateException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentV5Terminal
 * JD-Core Version:    0.5.4
 */