package javax.telephony.callcenter;

import javax.telephony.ConnectionEvent;

public abstract interface CallCenterConnectionEvent extends CallCenterCallEvent, ConnectionEvent
{
  public static final int CALLCENT_CONNECTION_IN_PROGRESS = 319;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.CallCenterConnectionEvent
 * JD-Core Version:    0.5.4
 */