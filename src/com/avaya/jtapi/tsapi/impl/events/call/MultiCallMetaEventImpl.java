package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.Call;
import javax.telephony.MetaEvent;
import javax.telephony.MultiCallMetaEvent;

public class MultiCallMetaEventImpl implements MultiCallMetaEvent {
	private CallEventParams callEventParams;
	private int id;

	public MultiCallMetaEventImpl(CallEventParams params, int id) {
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
		if (callEventParams.getOldCalls() == null) {
			return null;
		}
		Call[] oldCallArray = new Call[callEventParams.getOldCalls().size()];
		return callEventParams.getOldCalls().toArray(oldCallArray);
	}

	public Object getSource() {
		return callEventParams.getCall();
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.call.MultiCallMetaEventImpl JD-Core
 * Version: 0.5.4
 */