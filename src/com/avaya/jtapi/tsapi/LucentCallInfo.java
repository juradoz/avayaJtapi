package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.CallCenterTrunk;

public abstract interface LucentCallInfo extends ITsapiCallInfo
{
  public static final short AR_NONE = 0;
  public static final short AR_ANSWER_NORMAL = 1;
  public static final short AR_ANSWER_TIMED = 2;
  public static final short AR_ANSWER_VOICE_ENERGY = 3;
  public static final short AR_ANSWER_MACHINE_DETECTED = 4;
  public static final short AR_SIT_REORDER = 5;
  public static final short AR_SIT_NO_CIRCUIT = 6;
  public static final short AR_SIT_INTERCEPT = 7;
  public static final short AR_SIT_VACANT_CODE = 8;
  public static final short AR_SIT_INEFFECTIVE_OTHER = 9;
  public static final short AR_SIT_UNKNOWN = 10;
  public static final short AR_IN_QUEUE = 11;

  public abstract UserToUserInfo getUserToUserInfo();

  public abstract LookaheadInfo getLookaheadInfo();

  public abstract UserEnteredCode getUserEnteredCode();

  public abstract OriginalCallInfo getOriginalCallInfo();

  public abstract CallCenterAddress getDistributingAddress();

  public abstract ACDAddress getDeliveringACDAddress();

  public abstract CallCenterTrunk getTrunk();

  public abstract short getReason();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentCallInfo
 * JD-Core Version:    0.5.4
 */