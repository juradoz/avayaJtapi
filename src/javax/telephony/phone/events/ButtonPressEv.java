package javax.telephony.phone.events;

/** @deprecated */
public abstract interface ButtonPressEv extends PhoneTermEv {
	public static final int ID = 501;

	public abstract String getInfo();
}