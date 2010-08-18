package javax.telephony.privatedata;

import javax.telephony.ProviderListener;

public abstract interface PrivateDataProviderListener extends ProviderListener {
	public abstract void providerPrivateData(
			PrivateDataEvent paramPrivateDataEvent);
}

