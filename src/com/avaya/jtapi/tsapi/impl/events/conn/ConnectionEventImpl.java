package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.Connection;
import javax.telephony.ConnectionEvent;
import javax.telephony.MetaEvent;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class ConnectionEventImpl extends CallEventImpl implements
		ConnectionEvent {
	public ConnectionEventImpl(CallEventParams params, MetaEvent event,
			int eventId) {
		super(params, event, eventId);
	}

	public Connection getConnection() {
		Connection connection = null;
		if (callEventParams instanceof ConnEventParams) {
			connection = ((ConnEventParams) callEventParams).getConnection();
		}
		return connection;
	}

	@Override
	public Object getSource() {
		if (callEventParams instanceof ConnEventParams) {
			return ((ConnEventParams) callEventParams).getConnection();
		}
		return super.getSource();
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.conn.ConnectionEventImpl JD-Core Version:
 * 0.5.4
 */