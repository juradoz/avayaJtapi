package javax.telephony.phone;

import javax.telephony.InvalidArgumentException;

public abstract interface PhoneMicrophone extends Component
{
  public static final int MUTE = 0;
  public static final int MID = 50;
  public static final int FULL = 100;

  public abstract int getGain();

  public abstract void setGain(int paramInt)
    throws InvalidArgumentException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.phone.PhoneMicrophone
 * JD-Core Version:    0.5.4
 */