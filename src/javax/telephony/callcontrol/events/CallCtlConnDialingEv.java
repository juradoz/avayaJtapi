package javax.telephony.callcontrol.events;

/** @deprecated */
public abstract interface CallCtlConnDialingEv extends CallCtlConnEv {
	public static final int ID = 204;

	public abstract String getDigits();
}