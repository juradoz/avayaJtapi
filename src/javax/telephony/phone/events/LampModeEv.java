package javax.telephony.phone.events;

/** @deprecated */
@Deprecated
public abstract interface LampModeEv extends PhoneTermEv {
	public static final int ID = 504;

	public abstract int getMode();
}

