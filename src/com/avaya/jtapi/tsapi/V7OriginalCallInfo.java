package com.avaya.jtapi.tsapi;

public abstract interface V7OriginalCallInfo extends V5OriginalCallInfo
{
  public abstract V7DeviceHistoryEntry[] getDeviceHistory();

  public abstract boolean hasDeviceHistory();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.V7OriginalCallInfo
 * JD-Core Version:    0.5.4
 */