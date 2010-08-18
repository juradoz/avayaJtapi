package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class TermPrivConfHandler implements ConfHandler {
	TSDevice device;
	int pdu;

	TermPrivConfHandler(TSDevice _device, int _pdu) {
		device = _device;
		pdu = _pdu;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null) || (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != pdu)) {
			return;
		}

		device.replyTermPriv = event.getPrivData();
	}
}

