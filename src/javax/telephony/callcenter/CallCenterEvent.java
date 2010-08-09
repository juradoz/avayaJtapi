package javax.telephony.callcenter;

import javax.telephony.Event;

public abstract interface CallCenterEvent extends Event {
	public static final int CAUSE_NO_AVAILABLE_AGENTS = 302;

	public abstract int getCallCenterCause();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcenter.CallCenterEvent JD-Core Version: 0.5.4
 */