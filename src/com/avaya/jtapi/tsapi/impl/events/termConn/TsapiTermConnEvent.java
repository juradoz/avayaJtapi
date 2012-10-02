package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent;
import javax.telephony.TerminalConnection;

public abstract class TsapiTermConnEvent extends TsapiCallEvent {
	public final TerminalConnection getTerminalConnection() {
		return ((TermConnEventParams) this.params).getTermConn();
	}

	public TsapiTermConnEvent(TermConnEventParams params, int _eventPackage) {
		super(params, _eventPackage);
	}

	public TsapiTermConnEvent(TermConnEventParams params) {
		this(params, 0);
	}
}