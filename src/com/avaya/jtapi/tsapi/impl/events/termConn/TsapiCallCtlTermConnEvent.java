package com.avaya.jtapi.tsapi.impl.events.termConn;

public abstract class TsapiCallCtlTermConnEvent extends TsapiTermConnEvent {
	public TsapiCallCtlTermConnEvent(TermConnEventParams params) {
		super(params, 1);
	}
}