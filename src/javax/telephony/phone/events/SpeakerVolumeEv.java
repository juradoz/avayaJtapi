package javax.telephony.phone.events;

/** @deprecated */
public abstract interface SpeakerVolumeEv extends PhoneTermEv {
	public static final int ID = 508;

	public abstract int getVolume();
}