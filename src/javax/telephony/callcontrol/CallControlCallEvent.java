package javax.telephony.callcontrol;

import javax.telephony.Address;
import javax.telephony.CallEvent;
import javax.telephony.Terminal;

public abstract interface CallControlCallEvent extends CallControlEvent,
		CallEvent {
	public abstract Address getCalledAddress();

	public abstract Address getCallingAddress();

	public abstract Terminal getCallingTerminal();

	public abstract Address getLastRedirectedAddress();
}

