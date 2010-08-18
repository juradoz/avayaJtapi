package javax.telephony.phone.events;

/** @deprecated */
@Deprecated
public abstract interface MicrophoneGainEv extends PhoneTermEv {
	public static final int ID = 505;

	public abstract int getGain();
}
