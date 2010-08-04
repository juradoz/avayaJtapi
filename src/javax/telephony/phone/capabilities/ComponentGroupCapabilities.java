package javax.telephony.phone.capabilities;

import javax.telephony.Address;

public abstract interface ComponentGroupCapabilities
{
  public abstract boolean canActivate();

  public abstract boolean canActivate(Address paramAddress);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.phone.capabilities.ComponentGroupCapabilities
 * JD-Core Version:    0.5.4
 */