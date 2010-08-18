package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAConsultationCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAMakeCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAMakePredictiveCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentConsultationCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentMakeCallConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentMakePredictiveCallConfEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class MakeCallConfHandler implements ConfHandler {
	TSCall call;
	TSDevice device;
	String dialedDigits;
	CSTAConnectionID newCall;
	int pdu;

	MakeCallConfHandler(TSCall _call, TSDevice _device, String _dialedDigits,
			int _pdu) {
		call = _call;
		device = _device;
		dialedDigits = _dialedDigits;
		pdu = _pdu;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null) || (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != pdu)) {
			return;
		}

		call.replyPriv = event.getPrivData();

		switch (pdu) {
		case 24:
			newCall = ((CSTAMakeCallConfEvent) event.getEvent()).getNewCall();
			if (call.replyPriv instanceof LucentMakeCallConfEvent) {
				call.setUCID(((LucentMakeCallConfEvent) call.replyPriv)
						.getUcid());
			}
			break;
		case 26:
			newCall = ((CSTAMakePredictiveCallConfEvent) event.getEvent())
					.getNewCall();
			if (call.replyPriv instanceof LucentMakePredictiveCallConfEvent) {
				call
						.setUCID(((LucentMakePredictiveCallConfEvent) call.replyPriv)
								.getUcid());
			}
			break;
		case 14:
			newCall = ((CSTAConsultationCallConfEvent) event.getEvent())
					.getNewCall();
			if (call.replyPriv instanceof LucentConsultationCallConfEvent) {
				call.setUCID(((LucentConsultationCallConfEvent) call.replyPriv)
						.getUcid());
			}

		}

		call.setCallID(newCall.getCallID());
		call.setCallingDevices(device);
		TSDevice dialedDevice = call.getTSProviderImpl().createDevice(
				dialedDigits);
		call.setCalledDevice(dialedDevice);
	}
}

