package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.Address;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class CallControlTerminalConnectionEventImpl extends
		TerminalConnectionEventImpl implements
		CallControlTerminalConnectionEvent {
	public CallControlTerminalConnectionEventImpl(final CallEventParams params,
			final MetaEvent event, final int eventId) {
		super(params, event, eventId);
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

	public Address getLastRedirectedAddress() {
		return callEventParams.getLastRedirectionAddress();
	}
}
