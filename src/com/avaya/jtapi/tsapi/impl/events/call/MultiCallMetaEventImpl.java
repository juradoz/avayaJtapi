package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.Call;
import javax.telephony.MetaEvent;
import javax.telephony.MultiCallMetaEvent;

public class MultiCallMetaEventImpl implements MultiCallMetaEvent {
	private final CallEventParams callEventParams;
	private final int id;

	public MultiCallMetaEventImpl(final CallEventParams params, final int id) {
		callEventParams = params;
		this.id = id;
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

	public Call getNewCall() {
		return callEventParams.getCall();
	}

	public Call[] getOldCalls() {
		if (callEventParams.getOldCalls() == null)
			return null;
		final Call[] oldCallArray = new Call[callEventParams.getOldCalls()
				.size()];
		return callEventParams.getOldCalls().toArray(oldCallArray);
	}

	public Object getSource() {
		return callEventParams.getCall();
	}
}
