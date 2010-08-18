package javax.telephony.phone.events;

/** @deprecated */
@Deprecated
public abstract interface ButtonInfoEv extends PhoneTermEv {
	public static final int ID = 500;

	public abstract String getInfo();

	public abstract String getOldInfo();
}

