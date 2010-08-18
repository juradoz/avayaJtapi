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

	FwdConfHandler(TSDevice _device) {
		device = _device;
		pdu = 32;
	}

	FwdConfHandler(TSDevice _device, CSTAForwardingInfo[] _fwdInfo) {
		device = _device;
		pdu = 48;
		fwdInfo = _fwdInfo;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null) || (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != pdu)) {
			return;
		}

		if (pdu == 32) {
			fwdInfo = ((CSTAQueryFwdConfEvent) event.getEvent()).getForward();
		}

		device.replyAddrPriv = event.getPrivData();

		Vector<TSEvent> eventList = new Vector<TSEvent>();
		device.updateForwarding(fwdInfo, eventList);

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
 * com.avaya.jtapi.tsapi.impl.core.FwdConfHandler JD-Core Version: 0.5.4
 */