package javax.telephony.mobile;

import javax.telephony.ProviderListener;

public abstract interface MobileProviderListener extends ProviderListener {
	public abstract void networkChanged(
			MobileProviderEvent paramMobileProviderEvent);

	public abstract void serviceRestricted(
			MobileProviderEvent paramMobileProviderEvent);
}

