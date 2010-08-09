package javax.telephony;

public abstract interface ProviderEvent extends Event {
	public static final int PROVIDER_IN_SERVICE = 111;
	public static final int PROVIDER_EVENT_TRANSMISSION_ENDED = 112;
	public static final int PROVIDER_OUT_OF_SERVICE = 113;
	public static final int PROVIDER_SHUTDOWN = 114;

	public abstract Provider getProvider();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.ProviderEvent JD-Core Version: 0.5.4
 */