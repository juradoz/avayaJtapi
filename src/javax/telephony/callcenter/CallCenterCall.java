package javax.telephony.callcenter;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.Connection;
import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidPartyException;
import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PrivilegeViolationException;
import javax.telephony.ResourceUnavailableException;
import javax.telephony.Terminal;

public abstract interface CallCenterCall extends Call
{
  public static final int MIN_RINGS = 2;
  public static final int MAX_RINGS = 15;
  public static final int ANSWERING_TREATMENT_PROVIDER_DEFAULT = 1;
  public static final int ANSWERING_TREATMENT_DROP = 2;
  public static final int ANSWERING_TREATMENT_CONNECT = 3;
  public static final int ANSWERING_TREATMENT_NONE = 4;
  public static final int ENDPOINT_ANSWERING_MACHINE = 1;
  public static final int ENDPOINT_FAX_MACHINE = 2;
  public static final int ENDPOINT_HUMAN_INTERVENTION = 3;
  public static final int ENDPOINT_ANY = 4;

  public abstract Connection[] connectPredictive(Terminal paramTerminal, Address paramAddress, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws ResourceUnavailableException, PrivilegeViolationException, InvalidPartyException, InvalidArgumentException, InvalidStateException, MethodNotSupportedException;

  public abstract void setApplicationData(Object paramObject)
    throws ResourceUnavailableException, InvalidArgumentException, InvalidStateException, MethodNotSupportedException;

  public abstract Object getApplicationData()
    throws MethodNotSupportedException;

  public abstract CallCenterTrunk[] getTrunks()
    throws MethodNotSupportedException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.CallCenterCall
 * JD-Core Version:    0.5.4
 */