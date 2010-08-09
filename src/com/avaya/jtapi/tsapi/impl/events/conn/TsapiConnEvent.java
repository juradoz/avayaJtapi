package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.Connection;

import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent;

public abstract class TsapiConnEvent extends TsapiCallEvent {
	public TsapiConnEvent(ConnEventParams params) {
		this(params, 0);
	}

	public TsapiConnEvent(ConnEventParams params, int eventPackage) {
		super(params, eventPackage);
	}

	public final Connection getConnection() {
		return ((ConnEventParams) params).getConnection();
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnEvent JD-Core Version: 0.5.4
 */