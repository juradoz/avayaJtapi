package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class DisconnectedConfHandler implements ConfHandler {
	TSConnection conn;
	boolean handleIt = true;
	int pdu;

	DisconnectedConfHandler(final TSConnection _conn, final int _pdu) {
		conn = _conn;
		pdu = _pdu;
	}

	public void handleConf(final CSTAEvent event) {
		if (!handleIt || event == null
				|| event.getEventHeader().getEventClass() != 5
				|| event.getEventHeader().getEventType() != pdu)
			return;

		conn.replyConnPriv = event.getPrivData();

		final Vector<TSEvent> eventList = new Vector<TSEvent>();
		conn.setConnectionState(89, eventList);

		if (eventList.size() <= 0)
			return;
		final Vector<TsapiCallMonitor> observers = conn.getTSCall()
				.getObservers();
		for (int j = 0; j < observers.size(); ++j) {
			final TsapiCallMonitor callback = observers.elementAt(j);
			callback.deliverEvents(eventList, 100, false);
		}
	}
}
