package javax.telephony.phone;

public abstract interface PhoneButton extends Component
{
  public abstract String getInfo();

  public abstract void setInfo(String paramString);

  public abstract PhoneLamp getAssociatedPhoneLamp();

  public abstract void buttonPress();

  public abstract int setButtonPressThreshold(int paramInt);

  public abstract int getButtonPressThreshold();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.phone.PhoneButton
 * JD-Core Version:    0.5.4
 */