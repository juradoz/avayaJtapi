package javax.telephony.callcenter;

import javax.telephony.Call;

public abstract interface CallCenterTrunk
{
  public static final int INVALID_TRUNK = 1;
  public static final int VALID_TRUNK = 2;
  public static final int INCOMING_TRUNK = 1;
  public static final int OUTGOING_TRUNK = 2;
  public static final int UNKNOWN_TRUNK = 3;

  public abstract String getName();

  public abstract int getState();

  public abstract int getType();

  public abstract Call getCall();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.CallCenterTrunk
 * JD-Core Version:    0.5.4
 */