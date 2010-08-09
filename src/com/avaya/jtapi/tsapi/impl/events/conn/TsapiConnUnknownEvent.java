package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.events.ConnUnknownEv;

public final class TsapiConnUnknownEvent extends TsapiConnEvent implements
		ConnUnknownEv {
	public TsapiConnUnknownEvent(ConnEventParams params) {
		super(params);
	}

	public int getID() {
		return 110;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnUnknownEvent JD-Core Version:
 * 0.5.4
 */