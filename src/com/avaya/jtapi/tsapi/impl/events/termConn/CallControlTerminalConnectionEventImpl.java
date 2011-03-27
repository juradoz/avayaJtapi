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
	public Address getLastRedirectedAddress() {
		return callEventParams.getLastRedirectionAddress();
	}
}
