package com.avaya.jtapi.tsapi;

import javax.telephony.Call;
import javax.telephony.callcenter.RouteSession;

public abstract interface ITsapiRouteSession extends RouteSession, ITsapiCallInfo
{
  public abstract Call getCall();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.ITsapiRouteSession
 * JD-Core Version:    0.5.4
 */