package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAHoldCallConfEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class HoldConfHandler implements ConfHandler {
	TSConnection conn;

	HoldConfHandler(TSConnection _conn) {
		conn = _conn;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTAHoldCallConfEvent))) {
			return;
		}

		conn.replyTermConnPriv = event.getPrivData();

		Vector eventList = new Vector();
		conn.setConnectionState(88, eventList);
		conn.setTermConnState(99, eventList);
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
 * com.avaya.jtapi.tsapi.impl.core.HoldConfHandler JD-Core Version: 0.5.4
 */