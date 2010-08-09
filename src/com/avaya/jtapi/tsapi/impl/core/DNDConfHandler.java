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

	DNDConfHandler(TSDevice _device) {
		device = _device;
		pdu = 30;
	}

	DNDConfHandler(TSDevice _device, boolean _enable) {
		device = _device;
		pdu = 46;
		enable = _enable;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null) || (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != pdu)) {
			return;
		}

		if (pdu == 30) {
			enable = ((CSTAQueryDndConfEvent) event.getEvent())
					.isDoNotDisturb();
		}

		device.replyAddrPriv = event.getPrivData();
		device.replyTermPriv = event.getPrivData();

		Vector eventList = new Vector();

		device.updateDNDState(enable, eventList);

		if (eventList.size() <= 0) {
			return;
		}
		Vector observers = device.getAddressObservers();
		for (int j = 0; j < observers.size(); ++j) {
			TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
					.elementAt(j);
			callback.deliverEvents(eventList, false);
		}
		Vector terminalObservers = device.getTerminalObservers();
		for (int j = 0; j < terminalObservers.size(); ++j) {
			TsapiTerminalMonitor callback = (TsapiTerminalMonitor) terminalObservers
					.elementAt(j);
			callback.deliverEvents(eventList, false);
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.DNDConfHandler JD-Core Version: 0.5.4
 */