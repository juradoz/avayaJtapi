package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnCreatedEv;

public final class TsapiConnCreatedEvent extends TsapiConnEvent implements
		ConnCreatedEv {
	public TsapiConnCreatedEvent(ConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 106;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnCreatedEvent JD-Core Version:
 * 0.5.4
 */