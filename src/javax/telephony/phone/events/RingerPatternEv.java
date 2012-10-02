package javax.telephony.phone.events;

/** @deprecated */
public abstract interface RingerPatternEv extends PhoneTermEv {
	public static final int ID = 506;

	public abstract int getRingerPattern();
}