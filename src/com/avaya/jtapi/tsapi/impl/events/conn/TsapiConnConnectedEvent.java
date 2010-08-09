package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnConnectedEv;

public final class TsapiConnConnectedEvent extends TsapiConnEvent implements
		ConnConnectedEv {
	public TsapiConnConnectedEvent(ConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 105;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnConnectedEvent JD-Core
 * Version: 0.5.4
 */