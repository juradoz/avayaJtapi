package javax.telephony;

public abstract interface AddressEvent extends Event
{
  public static final int ADDRESS_EVENT_TRANSMISSION_ENDED = 100;

  public abstract Address getAddress();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.AddressEvent
 * JD-Core Version:    0.5.4
 */