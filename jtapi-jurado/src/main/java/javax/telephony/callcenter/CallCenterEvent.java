package javax.telephony.callcenter;

import javax.telephony.Event;

public abstract interface CallCenterEvent extends Event {
	public static final int CAUSE_NO_AVAILABLE_AGENTS = 302;

	public abstract int getCallCenterCause();
}