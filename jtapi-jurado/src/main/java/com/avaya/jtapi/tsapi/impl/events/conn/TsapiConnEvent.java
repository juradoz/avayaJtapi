package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent;
import javax.telephony.Connection;

public abstract class TsapiConnEvent extends TsapiCallEvent {
	public final Connection getConnection() {
		return ((ConnEventParams) this.params).getConnection();
	}

	public TsapiConnEvent(ConnEventParams params, int eventPackage) {
		super(params, eventPackage);
	}

	public TsapiConnEvent(ConnEventParams params) {
		this(params, 0);
	}
}