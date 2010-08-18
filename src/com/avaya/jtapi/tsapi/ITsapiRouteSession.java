package com.avaya.jtapi.tsapi;

import javax.telephony.Call;
import javax.telephony.callcenter.RouteSession;

public abstract interface ITsapiRouteSession extends RouteSession,
		ITsapiCallInfo {
	public abstract Call getCall();
}

