package javax.telephony;

public abstract interface JtapiPeer {
	public abstract String getName();

	public abstract Provider getProvider(String paramString)
			throws ProviderUnavailableException;

	public abstract String[] getServices();
}

