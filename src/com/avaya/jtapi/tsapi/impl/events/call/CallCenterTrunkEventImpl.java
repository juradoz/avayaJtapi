package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.Address;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.callcenter.CallCenterTrunkEvent;

public class CallCenterTrunkEventImpl extends CallEventImpl implements
		CallCenterTrunkEvent {
	public CallCenterTrunkEventImpl(final CallEventParams params,
			final MetaEvent event, final int eventId) {
		super(params, event, eventId);
	}

	@Override
	public Object getApplicationData() {
		return null;
	}

	@Override
	public int getCallCenterCause() {
		return callEventParams.getCause();
	}

	@Override
	public Address getCalledAddress() {
		return callEventParams.getCalledAddress();
	}

	@Override
	public Address getCallingAddress() {
		return callEventParams.getCallingAddress();
	}

	@Override
	public Terminal getCallingTerminal() {
		return callEventParams.getCallingTerminal();
	}

	@Override
	public Address getLastRedirectedAddress() {
		return callEventParams.getLastRedirectionAddress();
	}

	@Override
	public CallCenterTrunk getTrunk() {
		return callEventParams.getTrunk();
	}
}
