package com.avaya.jtapi.tsapi.impl.events.conn;

public abstract class TsapiCallCtlConnEvent extends TsapiConnEvent {
	public TsapiCallCtlConnEvent(final ConnEventParams params) {
		super(params, 1);
	}
}
