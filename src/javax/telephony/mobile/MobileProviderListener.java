package javax.telephony.mobile;

import javax.telephony.ProviderListener;

public abstract interface MobileProviderListener extends ProviderListener
{
  public abstract void serviceRestricted(MobileProviderEvent paramMobileProviderEvent);

  public abstract void networkChanged(MobileProviderEvent paramMobileProviderEvent);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.mobile.MobileProviderListener
 * JD-Core Version:    0.5.4
 */