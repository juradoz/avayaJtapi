package javax.telephony;

public abstract interface JtapiPeer {
	public abstract String getName();

	public abstract Provider getProvider(String paramString)
			throws ProviderUnavailableException;

	public abstract String[] getServices();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.JtapiPeer JD-Core Version: 0.5.4
 */