package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.Address;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.callcenter.CallCenterCallEvent;

public class CallCenterCallApplicationDataEventImpl extends CallEventImpl
		implements CallCenterCallEvent {
	private Object applicationData;

	public CallCenterCallApplicationDataEventImpl(CallEventParams params,
			MetaEvent event, int eventId, Object applicationData) {
		super(params, event, eventId);
		this.applicationData = applicationData;
	}

	public int getCallCenterCause() {
		return this.callEventParams.getCause();
	}

	public Address getCallingAddress() {
		return this.callEventParams.getCallingAddress();
	}

	public Terminal getCallingTerminal() {
		return this.callEventParams.getCallingTerminal();
	}

	public Address getCalledAddress() {
		return this.callEventParams.getCalledAddress();
	}

	public Address getLastRedirectedAddress() {
		return this.callEventParams.getLastRedirectionAddress();
	}

	public Object getApplicationData() {
		return this.applicationData;
	}
}