package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.CallCapabilities;

public abstract interface CallCenterCallCapabilities extends CallCapabilities
{
  public abstract boolean canConnectPredictive();

  public abstract boolean canHandleApplicationData();

  public abstract boolean canGetTrunks();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.capabilities.CallCenterCallCapabilities
 * JD-Core Version:    0.5.4
 */