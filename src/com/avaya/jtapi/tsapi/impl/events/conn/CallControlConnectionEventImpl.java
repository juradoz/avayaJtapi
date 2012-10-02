package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import javax.telephony.Address;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.callcontrol.CallControlConnectionEvent;

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

	public String getDigits() {
		return this.digits;
	}

	public int getNumberInQueue() {
		return this.numInQueue;
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

	public int getCallControlCause() {
		int cause = this.callEventParams.getCause();
		if ((cause == 101) || (cause == 202) || (cause == 203)
				|| (cause == 204) || (cause == 205) || (cause == 206)
				|| (cause == 207) || (cause == 208) || (cause == 209)
				|| (cause == 210) || (cause == 211) || (cause == 212)
				|| (cause == 213) || (cause == 214)) {
			return cause;
		}
		return 100;
	}
}