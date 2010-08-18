package com.avaya.jtapi.tsapi;

import javax.telephony.Call;
import javax.telephony.callcenter.CallCenterCall;
import javax.telephony.callcontrol.CallControlCall;

public abstract interface ITsapiCall extends ITsapiCallInfo, Call,
		CallControlCall, CallCenterCall {
}

