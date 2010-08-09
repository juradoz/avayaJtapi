package javax.telephony.phone.events;

import javax.telephony.events.Ev;

/** @deprecated */
@Deprecated
public abstract interface PhoneEv extends Ev {
	public static final int CAUSE_NORMAL = 500;
	public static final int CAUSE_UNKNOWN = 501;

	public abstract int getPhoneCause();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.phone.events.PhoneEv JD-Core Version: 0.5.4
 */