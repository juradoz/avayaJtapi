package com.avaya.jtapi.tsapi.tsapiInterface;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;

public abstract interface TsapiEventHandler {
	public abstract void close();

	public abstract void handleEvent(CSTAEvent paramCSTAEvent);

	public abstract void setUnsolicitedHandler(
			TsapiUnsolicitedHandler paramTsapiUnsolicitedHandler);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TsapiEventHandler JD-Core Version: 0.5.4
 */