package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.ProviderCapabilities;

public abstract interface CallCenterProviderCapabilities extends ProviderCapabilities
{
  public abstract boolean canGetRouteableAddresses();

  public abstract boolean canGetACDAddresses();

  public abstract boolean canGetACDManagerAddresses();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.capabilities.CallCenterProviderCapabilities
 * JD-Core Version:    0.5.4
 */