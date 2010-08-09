package com.avaya.jtapi.tsapi;

import javax.telephony.CallObserver;

public abstract interface LucentV7ACDManagerAddress extends
		LucentACDManagerAddress {
	public abstract void addPredictiveCallObserver(
			CallObserver paramCallObserver)
			throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentV7ACDManagerAddress JD-Core Version: 0.5.4
 */