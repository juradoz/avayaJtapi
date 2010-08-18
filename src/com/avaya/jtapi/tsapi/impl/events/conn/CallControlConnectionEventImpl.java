package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.Address;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.callcontrol.CallControlConnectionEvent;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class CallControlConnectionEventImpl extends ConnectionEventImpl
		implements CallControlConnectionEvent {
	private String digits;
	private int numInQueue;

	public CallControlConnectionEventImpl(CallEventParams params,
			MetaEvent event, int eventId, int numInQueue, String digits) {
		super(params, event, eventId);
		this.numInQueue = numInQueue;
		this.digits = digits;
	}

	public int getCallControlCause() {
		return callEventParams.getCause();
	}

	public Address getCalledAddress() {
		return callEventParams.getCalledAddress();
	}

	public Address getCallingAddress() {
		return callEventParams.getCallingAddress();
	}

	public Terminal getCallingTerminal() {
		return callEventParams.getCallingTerminal();
	}

	public String getDigits() {
		return digits;
	}

	public Address getLastRedirectedAddress() {
		return callEventParams.getLastRedirectionAddress();
	}

	public int getNumberInQueue() {
		return numInQueue;
	}
}
