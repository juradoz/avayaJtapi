package javax.telephony.phone.events;

/** @deprecated */
public abstract interface ButtonInfoEv extends PhoneTermEv
{
  public static final int ID = 500;

  public abstract String getInfo();

  public abstract String getOldInfo();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.phone.events.ButtonInfoEv
 * JD-Core Version:    0.5.4
 */