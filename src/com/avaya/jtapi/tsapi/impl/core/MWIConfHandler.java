package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryMwiConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryMwiConfEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class MWIConfHandler implements ConfHandler {
	TSDevice device;
	int pdu;
	int bits;

	MWIConfHandler(final TSDevice _device) {
		device = _device;
		pdu = 28;
	}

	MWIConfHandler(final TSDevice _device, final int _bits) {
		device = _device;
		pdu = 44;
		bits = _bits;
	}

	public void handleConf(final CSTAEvent event) {
		if (event == null || event.getEventHeader().getEventClass() != 5
				|| event.getEventHeader().getEventType() != pdu)
			return;

		if (pdu == 28) {
			final boolean enable = ((CSTAQueryMwiConfEvent) event.getEvent())
					.isMessages();
			if (event.getPrivData() instanceof LucentQueryMwiConfEvent) {
				final LucentQueryMwiConfEvent luPrivData = (LucentQueryMwiConfEvent) event
						.getPrivData();
				bits = luPrivData.getApplicationType();
			} else if (enable)
				bits = -1;
			else
				bits = 0;
		}

		device.replyAddrPriv = event.getPrivData();

		final Vector<TSEvent> eventList = new Vector<TSEvent>();

		device.updateMessageWaitingBits(bits, eventList);

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
