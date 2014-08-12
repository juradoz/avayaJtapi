package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAClearConnectionConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import java.util.Vector;

final class BridgedConfHandler implements ConfHandler {
	TSConnection conn;

	BridgedConfHandler(TSConnection _conn) {
		this.conn = _conn;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTAClearConnectionConfEvent))) {
			return;
		}

		this.conn.replyTermConnPriv = event.getPrivData();

		Vector<TSEvent> eventList = new Vector<TSEvent>();
		if (this.conn.getTSConn().getTermConns().size() > 1) {
			this.conn.setTermConnState(100, eventList);
		} else {
			this.conn.setTermConnState(102, eventList);
		}
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