package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAForwardingInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryFwdConfEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class FwdConfHandler implements ConfHandler {
	TSDevice device;
	int pdu;
	CSTAForwardingInfo[] fwdInfo;

	FwdConfHandler(final TSDevice _device) {
		device = _device;
		pdu = 32;
	}

	FwdConfHandler(final TSDevice _device, final CSTAForwardingInfo[] _fwdInfo) {
		device = _device;
		pdu = 48;
		fwdInfo = _fwdInfo;
	}

	@Override
	public void handleConf(final CSTAEvent event) {
		if (event == null || event.getEventHeader().getEventClass() != 5
				|| event.getEventHeader().getEventType() != pdu)
			return;

		if (pdu == 32)
			fwdInfo = ((CSTAQueryFwdConfEvent) event.getEvent()).getForward();

		device.replyAddrPriv = event.getPrivData();

		final Vector<TSEvent> eventList = new Vector<TSEvent>();
		device.updateForwarding(fwdInfo, eventList);

		if (eventList.size() <= 0)
			return;
		final Vector<TsapiAddressMonitor> observers = device
				.getAddressObservers();
		for (int j = 0; j < observers.size(); ++j) {
			final TsapiAddressMonitor callback = observers.elementAt(j);
			callback.deliverEvents(eventList, false);
		}
	}
}
