package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAClearCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import java.util.Vector;

final class ClearCallConfHandler implements ConfHandler {
	TSCall call;

	ClearCallConfHandler(TSCall _call) {
		this.call = _call;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTAClearCallConfEvent))) {
			return;
		}

		this.call.replyPriv = event.getPrivData();

		Vector<TSEvent> eventList = new Vector<TSEvent>();
		this.call.setState(34, eventList);
		if (eventList.size() > 0) {
			Vector<?> observers = this.call.getObservers();
			for (int j = 0; j < observers.size(); j++) {
				TsapiCallMonitor callback = (TsapiCallMonitor) observers
						.elementAt(j);
				callback.deliverEvents(eventList, 100, false);
			}
		}
		this.call.endCVDObservers(100, null);
	}
}