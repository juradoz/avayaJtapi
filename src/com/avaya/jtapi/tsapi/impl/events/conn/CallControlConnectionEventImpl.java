package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.Address;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.callcontrol.CallControlConnectionEvent;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class CallControlConnectionEventImpl extends ConnectionEventImpl
		implements CallControlConnectionEvent {
	private final String digits;
	private final int numInQueue;

	public CallControlConnectionEventImpl(final CallEventParams params,
			final MetaEvent event, final int eventId, final int numInQueue,
			final String digits) {
		super(params, event, eventId);
		this.numInQueue = numInQueue;
		this.digits = digits;
	}

	@Override
	public int getCallControlCause() {
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
	public String getDigits() {
		return digits;
	}

	@Override
	public Address getLastRedirectedAddress() {
		return callEventParams.getLastRedirectionAddress();
	}

	@Override
	public int getNumberInQueue() {
		return numInQueue;
	}
}
