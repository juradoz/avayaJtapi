package javax.telephony;

public abstract interface AddressEvent extends Event {
	public static final int ADDRESS_EVENT_TRANSMISSION_ENDED = 100;

	public abstract Address getAddress();
}
