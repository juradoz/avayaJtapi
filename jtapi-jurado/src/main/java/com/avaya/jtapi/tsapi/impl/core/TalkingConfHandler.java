package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import java.util.Vector;

final class TalkingConfHandler implements ConfHandler {
	TSConnection conn;
	int pdu;

	TalkingConfHandler(TSConnection _conn, int _pdu) {
		this.conn = _conn;
		this.pdu = _pdu;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null) || (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != this.pdu)) {
			return;
		}

		this.conn.replyTermConnPriv = event.getPrivData();

		Vector<TSEvent> eventList = new Vector<TSEvent>();
		this.conn.setConnectionState(88, eventList);
		this.conn.setTermConnState(98, eventList);
		if (eventList.size() > 0) {
			Vector<?> observers = this.conn.getTSCall().getObservers();
			for (int j = 0; j < observers.size(); j++) {
				TsapiCallMonitor callback = (TsapiCallMonitor) observers
						.elementAt(j);
				callback.deliverEvents(eventList, 100, false);
			}
		}
	}
}