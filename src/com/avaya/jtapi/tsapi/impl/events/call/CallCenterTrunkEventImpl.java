package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.Address;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.callcenter.CallCenterTrunkEvent;

public class CallCenterTrunkEventImpl extends CallEventImpl implements
		CallCenterTrunkEvent {
	public CallCenterTrunkEventImpl(CallEventParams params, MetaEvent event,
			int eventId) {
		super(params, event, eventId);
	}

	public CallCenterTrunk getTrunk() {
		return this.callEventParams.getTrunk();
	}

	public Object getApplicationData() {
		return null;
	}

	public Address getCalledAddress() {
		return this.callEventParams.getCalledAddress();
	}

	public Address getCallingAddress() {
		return this.callEventParams.getCallingAddress();
	}

	public Terminal getCallingTerminal() {
		return this.callEventParams.getCallingTerminal();
	}

	public Address getLastRedirectedAddress() {
		return this.callEventParams.getLastRedirectionAddress();
	}

	public int getCallCenterCause() {
		int cause = this.callEventParams.getCause();
		if ((cause == 101) || (cause == 302)) {
			return cause;
		}
		return 100;
	}
}