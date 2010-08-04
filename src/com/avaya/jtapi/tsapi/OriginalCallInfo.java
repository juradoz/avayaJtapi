package com.avaya.jtapi.tsapi;

public abstract interface OriginalCallInfo
{
  public static final short OR_NONE = 0;
  public static final short OR_CONSULTATION = 1;
  public static final short OR_CONFERENCED = 2;
  public static final short OR_TRANSFERRED = 3;
  public static final short OR_NEW_CALL = 4;

  public abstract short getReason();

  public abstract ITsapiAddress getCallingDevice();

  public abstract ITsapiAddress getCalledDevice();

  public abstract TsapiTrunk getTrunk();

  public abstract UserToUserInfo getUserToUserInfo();

  public abstract LookaheadInfo getLookaheadInfo();

  public abstract UserEnteredCode getUserEnteredCode();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.OriginalCallInfo
 * JD-Core Version:    0.5.4
 */