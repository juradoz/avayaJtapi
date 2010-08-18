package javax.telephony.phone.events;

/** @deprecated */
@Deprecated
public abstract interface ButtonPressEv extends PhoneTermEv {
	public static final int ID = 501;

	public abstract String getInfo();
}

