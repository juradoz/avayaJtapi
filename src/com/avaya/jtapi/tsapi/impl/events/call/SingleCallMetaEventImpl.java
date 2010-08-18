package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.Call;
import javax.telephony.MetaEvent;
import javax.telephony.SingleCallMetaEvent;

public class SingleCallMetaEventImpl implements SingleCallMetaEvent {
	private final CallEventParams callEventParams;
	private final int id;

	public SingleCallMetaEventImpl(final CallEventParams params, final int id) {
		callEventParams = params;
		this.id = id;
	}

	public Call getCall() {
		return callEventParams.getCall();
	}

	public int getCause() {
		return callEventParams.getCause();
	}

	public int getID() {
		return id;
	}

	public MetaEvent getMetaEvent() {
		return null;
	}

	public Object getSource() {
		return callEventParams.getCall();
	}
}
