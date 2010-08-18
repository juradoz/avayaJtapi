package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAPickupCallConfEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class PickupConfHandler implements ConfHandler {
	TSDevice device;
	TSDevice terminalAddress;
	TSConnection pickConnection;

	PickupConfHandler(TSDevice _device, TSDevice _terminalAddress,
			TSConnection _pickConnection) {
		device = _device;
		terminalAddress = _terminalAddress;
		pickConnection = _pickConnection;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof CSTAPickupCallConfEvent))) {
			return;
		}

		device.replyTermPriv = event.getPrivData();

		Vector<TSEvent> eventList = new Vector<TSEvent>();
		if (terminalAddress == pickConnection.getTSDevice()) {
			pickConnection.setConnectionState(88, eventList);

			if (eventList.size() <= 0) {
				return;
			}
			TSCall pickCall = pickConnection.getTSCall();
			Vector<TsapiCallMonitor> observers = pickCall.getObservers();
			for (int j = 0; j < observers.size(); ++j) {
				TsapiCallMonitor callback = observers.elementAt(j);
				callback.deliverEvents(eventList, 100, false);
			}

		} else {
			pickConnection.setConnectionState(89, eventList);

			if (eventList.size() <= 0) {
				return;
			}
			TSCall pickCall = pickConnection.getTSCall();
			Vector<TsapiCallMonitor> observers = pickCall.getObservers();
			for (int j = 0; j < observers.size(); ++j) {
				TsapiCallMonitor callback = observers.elementAt(j);
				callback.deliverEvents(eventList, 100, false);
			}
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.PickupConfHandler JD-Core Version: 0.5.4
 */