package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class TermPrivConfHandler implements ConfHandler {
	TSDevice device;
	int pdu;

	TermPrivConfHandler(final TSDevice _device, final int _pdu) {
		device = _device;
		pdu = _pdu;
	}

	@Override
	public void handleConf(final CSTAEvent event) {
		if (event == null || event.getEventHeader().getEventClass() != 5
				|| event.getEventHeader().getEventType() != pdu)
			return;

		device.replyTermPriv = event.getPrivData();
	}
}
