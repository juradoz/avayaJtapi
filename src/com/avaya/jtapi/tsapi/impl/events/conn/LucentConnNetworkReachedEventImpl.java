package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentCallInfo;
import com.avaya.jtapi.tsapi.LucentConnNetworkReachedEvent;

public class LucentConnNetworkReachedEventImpl extends
		TsapiConnNetworkReachedEvent implements LucentConnNetworkReachedEvent,
		LucentCallInfo {
	public LucentConnNetworkReachedEventImpl(ConnEventParams params) {
		super(params);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.LucentConnNetworkReachedEventImpl
 * JD-Core Version: 0.5.4
 */