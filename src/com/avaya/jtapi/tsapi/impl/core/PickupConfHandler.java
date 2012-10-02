package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAPickupCallConfEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import java.util.Vector;

final class PickupConfHandler implements ConfHandler {
	TSDevice device;
	TSDevice terminalAddress;
	TSConnection pickConnection;

	PickupConfHandler(TSDevice _device, TSDevice _terminalAddress,
			TSConnection _pickConnection) {
		this.device = _device;
		this.terminalAddress = _terminalAddress;
		this.pickConnection = _pickConnection;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTAPickupCallConfEvent))) {
			return;
		}

		this.device.replyTermPriv = event.getPrivData();

		Vector<TSEvent> eventList = new Vector<TSEvent>();
		if (this.terminalAddress == this.pickConnection.getTSDevice()) {
			this.pickConnection.setConnectionState(88, eventList);

			if (eventList.size() > 0) {
				TSCall pickCall = this.pickConnection.getTSCall();
				Vector<?> observers = pickCall.getObservers();
				for (int j = 0; j < observers.size(); j++) {
					TsapiCallMonitor callback = (TsapiCallMonitor) observers
							.elementAt(j);
					callback.deliverEvents(eventList, 100, false);
				}
			}
		} else {
			this.pickConnection.setConnectionState(89, eventList);

			if (eventList.size() > 0) {
				TSCall pickCall = this.pickConnection.getTSCall();
				Vector<?> observers = pickCall.getObservers();
				for (int j = 0; j < observers.size(); j++) {
					TsapiCallMonitor callback = (TsapiCallMonitor) observers
							.elementAt(j);
					callback.deliverEvents(eventList, 100, false);
				}
			}
		}
	}
}