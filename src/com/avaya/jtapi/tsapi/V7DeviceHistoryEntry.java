package com.avaya.jtapi.tsapi;

public abstract interface V7DeviceHistoryEntry
{
  public abstract String getOldDeviceID();

  public abstract short getEventCause();

  public abstract ConnectionID getOldConnectionID();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.V7DeviceHistoryEntry
 * JD-Core Version:    0.5.4
 */