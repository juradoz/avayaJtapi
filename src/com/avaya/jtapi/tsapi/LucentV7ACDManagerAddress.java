package com.avaya.jtapi.tsapi;

import javax.telephony.CallObserver;

@SuppressWarnings("deprecation")
public abstract interface LucentV7ACDManagerAddress extends
		LucentACDManagerAddress {
	public abstract void addPredictiveCallObserver(
			CallObserver paramCallObserver)
			throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException;
}

