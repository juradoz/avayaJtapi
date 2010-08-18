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

	PickupConfHandler(final TSDevice _device, final TSDevice _terminalAddress,
			final TSConnection _pickConnection) {
		device = _device;
		terminalAddress = _terminalAddress;
		pickConnection = _pickConnection;
	}

	public void handleConf(final CSTAEvent event) {
		if (event == null
				|| !(event.getEvent() instanceof CSTAPickupCallConfEvent))
			return;

		device.replyTermPriv = event.getPrivData();

		final Vector<TSEvent> eventList = new Vector<TSEvent>();
		if (terminalAddress == pickConnection.getTSDevice()) {
			pickConnection.setConnectionState(88, eventList);

			if (eventList.size() <= 0)
				return;
			final TSCall pickCall = pickConnection.getTSCall();
			final Vector<TsapiCallMonitor> observers = pickCall.getObservers();
			for (int j = 0; j < observers.size(); ++j) {
				final TsapiCallMonitor callback = observers.elementAt(j);
				callback.deliverEvents(eventList, 100, false);
			}

		} else {
			pickConnection.setConnectionState(89, eventList);

			if (eventList.size() <= 0)
				return;
			final TSCall pickCall = pickConnection.getTSCall();
			final Vector<TsapiCallMonitor> observers = pickCall.getObservers();
			for (int j = 0; j < observers.size(); ++j) {
				final TsapiCallMonitor callback = observers.elementAt(j);
				callback.deliverEvents(eventList, 100, false);
			}
		}
	}
}
