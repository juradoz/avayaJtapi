package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.AddressCapabilities;

public abstract interface ACDAddressCapabilities extends AddressCapabilities
{
  public abstract boolean canGetLoggedOnAgents();

  public abstract boolean canGetNumberQueued();

  public abstract boolean canGetOldestCallQueued();

  public abstract boolean canGetRelativeQueueLoad();

  public abstract boolean canGetQueueWaitTime();

  public abstract boolean canGetACDManagerAddress();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.capabilities.ACDAddressCapabilities
 * JD-Core Version:    0.5.4
 */