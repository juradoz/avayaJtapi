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
	private final MetaEvent metaEvent;
	private final int id;

	public CallEventImpl(final CallEventParams params, final MetaEvent event,
			final int eventId) {
		callEventParams = params;
		metaEvent = event;
		id = eventId;
	}

	@Override
	public Call getCall() {
		return callEventParams.getCall();
	}

	@Override
	public int getCause() {
		return callEventParams.getCause();
	}

	@Override
	public short getCSTACause() {
		return callEventParams.getCstaCause();
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public MetaEvent getMetaEvent() {
		return metaEvent;
	}

	@Override
	public Object getPrivateData() {
		final Object privateData = callEventParams.getPrivateData();
		if (privateData instanceof TsapiPrivate
				|| privateData instanceof LucentChargeAdviceEvent
				|| privateData instanceof TsapiPrivateStateEvent)
			return privateData;
		return null;
	}

	@Override
	public Object getSource() {
		return callEventParams.getCall();
	}
}
