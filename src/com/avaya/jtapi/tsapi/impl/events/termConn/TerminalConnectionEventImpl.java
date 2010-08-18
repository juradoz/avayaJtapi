package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.MetaEvent;
import javax.telephony.TerminalConnection;
import javax.telephony.TerminalConnectionEvent;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import com.avaya.jtapi.tsapi.impl.events.conn.ConnectionEventImpl;

public class TerminalConnectionEventImpl extends ConnectionEventImpl implements
		TerminalConnectionEvent {
	public TerminalConnectionEventImpl(final CallEventParams params,
			final MetaEvent event, final int eventId) {
		super(params, event, eventId);
	}

	@Override
	public Object getSource() {
		if (callEventParams instanceof TermConnEventParams)
			return ((TermConnEventParams) callEventParams).getTermConn();
		return super.getSource();
	}

	public TerminalConnection getTerminalConnection() {
		TerminalConnection tc = null;
		if (callEventParams instanceof TermConnEventParams)
			tc = ((TermConnEventParams) callEventParams).getTermConn();
		return tc;
	}
}
