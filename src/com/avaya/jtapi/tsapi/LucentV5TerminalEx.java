package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.Agent;

public abstract interface LucentV5TerminalEx extends LucentV5Terminal
{
  public abstract void removeAgent(Agent paramAgent, int paramInt)
    throws TsapiInvalidArgumentException, TsapiInvalidStateException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentV5TerminalEx
 * JD-Core Version:    0.5.4
 */