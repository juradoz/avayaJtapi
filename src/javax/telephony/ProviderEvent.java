package javax.telephony;

public abstract interface ProviderEvent extends Event {
	public static final int PROVIDER_IN_SERVICE = 111;
	public static final int PROVIDER_EVENT_TRANSMISSION_ENDED = 112;
	public static final int PROVIDER_OUT_OF_SERVICE = 113;
	public static final int PROVIDER_SHUTDOWN = 114;

	public abstract Provider getProvider();
}

