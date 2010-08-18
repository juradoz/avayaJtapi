package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryDndConfEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class DNDConfHandler implements ConfHandler {
	TSDevice device;
	int pdu;
	boolean enable;

	DNDConfHandler(final TSDevice _device) {
		device = _device;
		pdu = 30;
	}

	DNDConfHandler(final TSDevice _device, final boolean _enable) {
		device = _device;
		pdu = 46;
		enable = _enable;
	}

	public void handleConf(final CSTAEvent event) {
		if (event == null || event.getEventHeader().getEventClass() != 5
				|| event.getEventHeader().getEventType() != pdu)
			return;

		if (pdu == 30)
			enable = ((CSTAQueryDndConfEvent) event.getEvent())
					.isDoNotDisturb();

		device.replyAddrPriv = event.getPrivData();
		device.replyTermPriv = event.getPrivData();

		final Vector<TSEvent> eventList = new Vector<TSEvent>();

		device.updateDNDState(enable, eventList);

		if (eventList.size() <= 0)
			return;
		final Vector<TsapiAddressMonitor> observers = device
				.getAddressObservers();
		for (int j = 0; j < observers.size(); ++j) {
			final TsapiAddressMonitor callback = observers.elementAt(j);
			callback.deliverEvents(eventList, false);
		}
		final Vector<TsapiTerminalMonitor> terminalObservers = device
				.getTerminalObservers();
		for (int j = 0; j < terminalObservers.size(); ++j) {
			final TsapiTerminalMonitor callback = terminalObservers
					.elementAt(j);
			callback.deliverEvents(eventList, false);
		}
	}
}
