package javax.telephony.callcontrol.events;

import javax.telephony.callcontrol.CallControlForwarding;

/** @deprecated */
@Deprecated
public abstract interface CallCtlAddrForwardEv extends CallCtlAddrEv {
	public static final int ID = 201;

	public abstract CallControlForwarding[] getForwarding();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.events.CallCtlAddrForwardEv JD-Core Version:
 * 0.5.4
 */