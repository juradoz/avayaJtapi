package com.avaya.jtapi.tsapi;

import javax.telephony.Address;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.RouteAddress;
import javax.telephony.callcontrol.CallControlAddress;

public abstract interface ITsapiAddress extends Address, CallControlAddress, CallCenterAddress, RouteAddress
{
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.ITsapiAddress
 * JD-Core Version:    0.5.4
 */