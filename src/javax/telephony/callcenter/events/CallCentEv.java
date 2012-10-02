package javax.telephony.callcenter.events;

import javax.telephony.events.Ev;

/** @deprecated */
public abstract interface CallCentEv extends Ev {
	public static final int CAUSE_NO_AVAILABLE_AGENTS = 302;

	public abstract int getCallCenterCause();
}