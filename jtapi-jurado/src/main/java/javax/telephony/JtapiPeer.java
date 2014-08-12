package javax.telephony;

public abstract interface JtapiPeer {
	public abstract String getName();

	public abstract String[] getServices();

	public abstract Provider getProvider(String paramString)
			throws ProviderUnavailableException;
}