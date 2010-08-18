package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.TerminalConnection;

import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent;

public abstract class TsapiTermConnEvent extends TsapiCallEvent {
	public TsapiTermConnEvent(TermConnEventParams params) {
		this(params, 0);
	}

	public TsapiTermConnEvent(TermConnEventParams params, int _eventPackage) {
		super(params, _eventPackage);
	}

	public final TerminalConnection getTerminalConnection() {
		return ((TermConnEventParams) params).getTermConn();
	}
}

