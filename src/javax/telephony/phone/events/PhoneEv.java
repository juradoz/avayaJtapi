package javax.telephony.phone.events;

import javax.telephony.events.Ev;

/** @deprecated */
@Deprecated
public abstract interface PhoneEv extends Ev {
	public static final int CAUSE_NORMAL = 500;
	public static final int CAUSE_UNKNOWN = 501;

	public abstract int getPhoneCause();
}
