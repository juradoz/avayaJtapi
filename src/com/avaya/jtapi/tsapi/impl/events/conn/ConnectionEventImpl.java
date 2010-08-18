package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.Connection;
import javax.telephony.ConnectionEvent;
import javax.telephony.MetaEvent;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventImpl;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class ConnectionEventImpl extends CallEventImpl implements
		ConnectionEvent {
	public ConnectionEventImpl(final CallEventParams params,
			final MetaEvent event, final int eventId) {
		super(params, event, eventId);
	}

	public Connection getConnection() {
		Connection connection = null;
		if (callEventParams instanceof ConnEventParams)
			connection = ((ConnEventParams) callEventParams).getConnection();
		return connection;
	}

	@Override
	public Object getSource() {
		if (callEventParams instanceof ConnEventParams)
			return ((ConnEventParams) callEventParams).getConnection();
		return super.getSource();
	}
}
