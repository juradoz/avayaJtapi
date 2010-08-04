package com.avaya.jtapi.tsapi;

import javax.telephony.Call;
import javax.telephony.callcenter.CallCenterCall;
import javax.telephony.callcontrol.CallControlCall;

public abstract interface ITsapiCall extends ITsapiCallInfo, Call, CallControlCall, CallCenterCall
{
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.ITsapiCall
 * JD-Core Version:    0.5.4
 */