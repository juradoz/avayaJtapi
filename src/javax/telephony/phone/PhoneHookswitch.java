package javax.telephony.phone;

import javax.telephony.InvalidArgumentException;

public abstract interface PhoneHookswitch extends Component
{
  public static final int ON_HOOK = 0;
  public static final int OFF_HOOK = 1;

  public abstract void setHookSwitch(int paramInt)
    throws InvalidArgumentException;

  public abstract int getHookSwitchState();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.phone.PhoneHookswitch
 * JD-Core Version:    0.5.4
 */