package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import javax.telephony.Address;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;

public class CallControlTerminalConnectionEventImpl extends
		TerminalConnectionEventImpl implements
		CallControlTerminalConnectionEvent {
	public CallControlTerminalConnectionEventImpl(CallEventParams params,
			MetaEvent event, int eventId) {
		super(params, event, eventId);
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