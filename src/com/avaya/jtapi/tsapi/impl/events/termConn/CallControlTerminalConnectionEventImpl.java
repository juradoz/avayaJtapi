package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.Address;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class CallControlTerminalConnectionEventImpl extends
		TerminalConnectionEventImpl implements
		CallControlTerminalConnectionEvent {
	public CallControlTerminalConnectionEventImpl(CallEventParams params,
			MetaEvent event, int eventId) {
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com
 * .avaya.jtapi.tsapi.impl.events.termConn.CallControlTerminalConnectionEventImpl
 * JD-Core Version: 0.5.4
 */