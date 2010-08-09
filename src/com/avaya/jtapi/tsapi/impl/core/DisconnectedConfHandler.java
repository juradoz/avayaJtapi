package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class DisconnectedConfHandler implements ConfHandler {
	TSConnection conn;
	boolean handleIt = true;
	int pdu;

	DisconnectedConfHandler(TSConnection _conn, int _pdu) {
		conn = _conn;
		pdu = _pdu;
	}

	public void handleConf(CSTAEvent event) {
		if ((!handleIt) || (event == null)
				|| (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != pdu)) {
			return;
		}

		conn.replyConnPriv = event.getPrivData();

		Vector eventList = new Vector();
		conn.setConnectionState(89, eventList);

		if (eventList.size() <= 0) {
			return;
		}
		Vector observers = conn.getTSCall().getObservers();
		for (int j = 0; j < observers.size(); ++j) {
			TsapiCallMonitor callback = (TsapiCallMonitor) observers
					.elementAt(j);
			callback.deliverEvents(eventList, 100, false);
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.DisconnectedConfHandler JD-Core Version:
 * 0.5.4
 */