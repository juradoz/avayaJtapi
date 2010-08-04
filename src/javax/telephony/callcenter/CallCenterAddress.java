package javax.telephony.callcenter;

import javax.telephony.Address;
import javax.telephony.CallObserver;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PrivilegeViolationException;
import javax.telephony.ResourceUnavailableException;

public abstract interface CallCenterAddress extends Address
{
  public abstract void addCallObserver(CallObserver paramCallObserver, boolean paramBoolean)
    throws ResourceUnavailableException, PrivilegeViolationException, MethodNotSupportedException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.CallCenterAddress
 * JD-Core Version:    0.5.4
 */