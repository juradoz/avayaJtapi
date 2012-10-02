package javax.telephony.callcenter;

import javax.telephony.Address;
import javax.telephony.CallEvent;
import javax.telephony.Terminal;

public abstract interface CallCenterCallEvent extends CallCenterEvent,
		CallEvent {
	public static final int CALLCENT_CALL_APPLICATION_DATA_EVENT = 316;

	public abstract Address getCallingAddress();

	public abstract Terminal getCallingTerminal();

	public abstract Address getCalledAddress();

	public abstract Address getLastRedirectedAddress();

	public abstract Object getApplicationData();
}