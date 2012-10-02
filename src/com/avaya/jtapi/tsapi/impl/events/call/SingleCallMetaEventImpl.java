package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.Call;
import javax.telephony.MetaEvent;
import javax.telephony.SingleCallMetaEvent;

public class SingleCallMetaEventImpl implements SingleCallMetaEvent {
	private CallEventParams callEventParams;
	private int id;

	public SingleCallMetaEventImpl(CallEventParams params, int id) {
		this.callEventParams = params;
		this.id = id;
	}

	public int getCause() {
		return this.callEventParams.getCause();
	}

	public int getID() {
		return this.id;
	}

	public MetaEvent getMetaEvent() {
		return null;
	}

	public Object getSource() {
		return this.callEventParams.getCall();
	}

	public Call getCall() {
		return this.callEventParams.getCall();
	}
}