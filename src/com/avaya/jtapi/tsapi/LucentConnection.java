package com.avaya.jtapi.tsapi;

public abstract interface LucentConnection extends ITsapiConnection
{
  public static final short DR_NONE = -1;
  public static final short DR_CALL_CLASSIFIER = 0;
  public static final short DR_TONE_GENERATOR = 1;

  public abstract void disconnect(short paramShort, UserToUserInfo paramUserToUserInfo)
    throws TsapiPrivilegeViolationException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException, TsapiInvalidStateException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.LucentConnection
 * JD-Core Version:    0.5.4
 */