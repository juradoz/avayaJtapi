package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryMwiConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryMwiConfEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import java.util.Vector;

final class MWIConfHandler implements ConfHandler {
	TSDevice device;
	int pdu;
	int bits;

	MWIConfHandler(TSDevice _device, int _bits) {
		this.device = _device;
		this.pdu = 44;
		this.bits = _bits;
	}

	MWIConfHandler(TSDevice _device) {
		this.device = _device;
		this.pdu = 28;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null) || (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != this.pdu)) {
			return;
		}

		if (this.pdu == 28) {
			boolean enable = ((CSTAQueryMwiConfEvent) event.getEvent())
					.isMessages();
			if ((event.getPrivData() instanceof LucentQueryMwiConfEvent)) {
				LucentQueryMwiConfEvent luPrivData = (LucentQueryMwiConfEvent) event
						.getPrivData();
				this.bits = luPrivData.getApplicationType();
			} else if (enable) {
				this.bits = -1;
			} else {
				this.bits = 0;
			}
		}

		this.device.replyAddrPriv = event.getPrivData();

		Vector<TSEvent> eventList = new Vector<TSEvent>();

		this.device.updateMessageWaitingBits(this.bits, eventList);

		if (eventList.size() > 0) {
			Vector<?> observers = this.device.getAddressObservers();
			for (int j = 0; j < observers.size(); j++) {
				TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
						.elementAt(j);
				callback.deliverEvents(eventList, false);
			}
		}
	}
}