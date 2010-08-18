package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.Connection;

import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent;

public abstract class TsapiConnEvent extends TsapiCallEvent {
	public TsapiConnEvent(final ConnEventParams params) {
		this(params, 0);
	}

	public TsapiConnEvent(final ConnEventParams params, final int eventPackage) {
		super(params, eventPackage);
	}

	public final Connection getConnection() {
		return ((ConnEventParams) params).getConnection();
	}
}
