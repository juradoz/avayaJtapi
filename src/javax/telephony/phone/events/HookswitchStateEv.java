package javax.telephony.phone.events;

/** @deprecated */
@Deprecated
public abstract interface HookswitchStateEv extends PhoneTermEv {
	public static final int ID = 503;

	public abstract int getHookSwitchState();
}
