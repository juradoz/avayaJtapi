package com.avaya.jtapi.tsapi.tsapiInterface;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;

public abstract interface TsapiEventHandler {
	public abstract void handleEvent(CSTAEvent paramCSTAEvent);

	public abstract void close();

	public abstract void setUnsolicitedHandler(
			TsapiUnsolicitedHandler paramTsapiUnsolicitedHandler);
}