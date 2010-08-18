package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.csta1.CSTAClearConnectionConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class BridgedConfHandler implements ConfHandler {
	TSConnection conn;

	BridgedConfHandler(TSConnection _conn) {
		conn = _conn;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTAClearConnectionConfEvent))) {
			return;
		}

		conn.replyTermConnPriv = event.getPrivData();

		Vector<TSEvent> eventList = new Vector<TSEvent>();
		if (conn.getTSConn().getTermConns().size() > 1) {
			conn.setTermConnState(100, eventList);
		} else {
			conn.setTermConnState(102, eventList);
		}
		if (eventList.size() <= 0) {
			return;
		}
		Vector<TsapiCallMonitor> observers = conn.getTSCall().getObservers();
		for (int j = 0; j < observers.size(); ++j) {
			TsapiCallMonitor callback = observers.elementAt(j);
			callback.deliverEvents(eventList, 100, false);
		}
	}
}

