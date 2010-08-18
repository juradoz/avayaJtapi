package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.Call;
import javax.telephony.CallEvent;
import javax.telephony.MetaEvent;
import javax.telephony.privatedata.PrivateDataEvent;

import com.avaya.jtapi.tsapi.ITsapiCallEvent;
import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;

public class CallEventImpl implements CallEvent, PrivateDataEvent,
		ITsapiCallEvent {
	protected CallEventParams callEventParams;
	private MetaEvent metaEvent;
	private int id;

	public CallEventImpl(CallEventParams params, MetaEvent event, int eventId) {
		callEventParams = params;
		metaEvent = event;
		id = eventId;
	}

	public Call getCall() {
		return callEventParams.getCall();
	}

	public int getCause() {
		return callEventParams.getCause();
	}

	public short getCSTACause() {
		return callEventParams.getCstaCause();
	}

	public int getID() {
		return id;
	}

	public MetaEvent getMetaEvent() {
		return metaEvent;
	}

	public Object getPrivateData() {
		Object privateData = callEventParams.getPrivateData();
		if ((privateData instanceof TsapiPrivate)
				|| (privateData instanceof LucentChargeAdviceEvent)
				|| (privateData instanceof TsapiPrivateStateEvent)) {
			return privateData;
		}
		return null;
	}

	public Object getSource() {
		return callEventParams.getCall();
	}
}

