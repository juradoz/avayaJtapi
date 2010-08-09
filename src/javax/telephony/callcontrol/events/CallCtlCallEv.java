package javax.telephony.callcontrol.events;

import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.events.CallEv;

/** @deprecated */
@Deprecated
public abstract interface CallCtlCallEv extends CallCtlEv, CallEv {
	public abstract Address getCalledAddress();

	public abstract Address getCallingAddress();

	public abstract Terminal getCallingTerminal();

	public abstract Address getLastRedirectedAddress();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.events.CallCtlCallEv JD-Core Version: 0.5.4
 */