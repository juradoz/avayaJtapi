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
		this.call = _call;
		this.device = _device;
		this.dialedDigits = _dialedDigits;
		this.pdu = _pdu;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null) || (event.getEventHeader().getEventClass() != 5)
				|| (event.getEventHeader().getEventType() != this.pdu)) {
			return;
		}

		this.call.replyPriv = event.getPrivData();

		switch (this.pdu) {
		case 24:
			this.newCall = ((CSTAMakeCallConfEvent) event.getEvent())
					.getNewCall();
			if ((this.call.replyPriv instanceof LucentMakeCallConfEvent)) {
				this.call
						.setUCID(((LucentMakeCallConfEvent) this.call.replyPriv)
								.getUcid());
			}
			break;
		case 26:
			this.newCall = ((CSTAMakePredictiveCallConfEvent) event.getEvent())
					.getNewCall();
			if ((this.call.replyPriv instanceof LucentMakePredictiveCallConfEvent)) {
				this.call
						.setUCID(((LucentMakePredictiveCallConfEvent) this.call.replyPriv)
								.getUcid());
			}
			break;
		case 14:
			this.newCall = ((CSTAConsultationCallConfEvent) event.getEvent())
					.getNewCall();
			if ((this.call.replyPriv instanceof LucentConsultationCallConfEvent)) {
				this.call
						.setUCID(((LucentConsultationCallConfEvent) this.call.replyPriv)
								.getUcid());
			}
			break;
		}

		this.call.setCallID(this.newCall.getCallID());
		this.call.setCallingDevices(this.device);
		TSDevice dialedDevice = this.call.getTSProviderImpl().createDevice(
				this.dialedDigits);
		this.call.setCalledDevice(dialedDevice);
	}
}