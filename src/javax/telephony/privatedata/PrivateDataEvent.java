package javax.telephony.privatedata;

import javax.telephony.Event;

public abstract interface PrivateDataEvent extends Event
{
  public static final int PRIVATE_DATA_ADDRESS_EVENT = 600;
  public static final int PRIVATE_DATA_CALL_EVENT = 601;
  public static final int PRIVATE_DATA_PROVIDER_EVENT = 602;
  public static final int PRIVATE_DATA_TERMINAL_EVENT = 603;

  public abstract Object getPrivateData();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.privatedata.PrivateDataEvent
 * JD-Core Version:    0.5.4
 */