package javax.telephony.privatedata;

import javax.telephony.ProviderListener;

public abstract interface PrivateDataProviderListener extends ProviderListener {
	public abstract void providerPrivateData(
			PrivateDataEvent paramPrivateDataEvent);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.privatedata.PrivateDataProviderListener JD-Core Version:
 * 0.5.4
 */