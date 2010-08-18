package javax.telephony.phone.events;

/** @deprecated */
@Deprecated
public abstract interface DisplayUpdateEv extends PhoneTermEv {
	public static final int ID = 502;

	public abstract String getDisplay(int paramInt1, int paramInt2);
}

