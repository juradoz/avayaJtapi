package com.avaya.jtapi.tsapi.impl.events.conn;

public abstract class TsapiCallCtrConnEvent extends TsapiConnEvent {
	public TsapiCallCtrConnEvent(final ConnEventParams params) {
		super(params, 2);
	}
}
