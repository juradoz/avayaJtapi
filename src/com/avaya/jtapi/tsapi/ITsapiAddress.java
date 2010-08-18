package com.avaya.jtapi.tsapi;

import javax.telephony.Address;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.RouteAddress;
import javax.telephony.callcontrol.CallControlAddress;

public abstract interface ITsapiAddress extends Address, CallControlAddress,
		CallCenterAddress, RouteAddress {
}
