package com.avaya.jtapi.tsapi.tsapiInterface;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;

public abstract interface TsapiUnsolicitedHandler {
	public abstract void acsUnsolicited(CSTAEvent paramCSTAEvent);

	public abstract void cstaUnsolicited(CSTAEvent paramCSTAEvent);

	public abstract void cstaRequest(CSTAEvent paramCSTAEvent);

	public abstract void cstaEventReport(CSTAEvent paramCSTAEvent);

	public abstract void eventDistributorException(Exception paramException);
}