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

	@Override
	public int getCause() {
		return callEventParams.getCause();
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public MetaEvent getMetaEvent() {
		return null;
	}

	@Override
	public Call getNewCall() {
		return callEventParams.getCall();
	}

	@Override
	public Call[] getOldCalls() {
		if (callEventParams.getOldCalls() == null)
			return null;
		final Call[] oldCallArray = new Call[callEventParams.getOldCalls()
				.size()];
		return callEventParams.getOldCalls().toArray(oldCallArray);
	}

	@Override
	public Object getSource() {
		return callEventParams.getCall();
	}
}
