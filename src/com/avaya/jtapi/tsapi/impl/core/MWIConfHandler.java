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

	MWIConfHandler(TSDevice _device) {
		device = _device;
		pdu = 28;
	}

	MWIConfHandler(TSDevice _device, int _bits) {
		device = _device;
		pdu = 44;
		bits = _bits;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null) || (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != pdu)) {
			return;
		}

		if (pdu == 28) {
			boolean enable = ((CSTAQueryMwiConfEvent) event.getEvent())
					.isMessages();
			if (event.getPrivData() instanceof LucentQueryMwiConfEvent) {
				LucentQueryMwiConfEvent luPrivData = (LucentQueryMwiConfEvent) event
						.getPrivData();
				bits = luPrivData.getApplicationType();
			} else if (enable) {
				bits = -1;
			} else {
				bits = 0;
			}
		}

		device.replyAddrPriv = event.getPrivData();

		Vector<TSEvent> eventList = new Vector<TSEvent>();

		device.updateMessageWaitingBits(bits, eventList);

		if (eventList.size() <= 0) {
			return;
		}
		Vector<TsapiAddressMonitor> observers = device.getAddressObservers();
		for (int j = 0; j < observers.size(); ++j) {
			TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
					.elementAt(j);
			callback.deliverEvents(eventList, false);
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.MWIConfHandler JD-Core Version: 0.5.4
 */