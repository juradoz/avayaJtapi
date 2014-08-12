package com.avaya.jtapi.tsapi.impl.events.conn;

public abstract class TsapiCallCtlConnEvent extends TsapiConnEvent {
	public TsapiCallCtlConnEvent(ConnEventParams params) {
		super(params, 1);
	}
}