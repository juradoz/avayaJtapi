package javax.telephony.mobile;

public abstract interface MobileRadioListener
{
  public abstract void signalLevelChanged(MobileRadioEvent paramMobileRadioEvent);

  public abstract void radioStartStateOn(MobileRadioEvent paramMobileRadioEvent);

  public abstract void radioStartStateOff(MobileRadioEvent paramMobileRadioEvent);

  public abstract void radioOn(MobileRadioEvent paramMobileRadioEvent);

  public abstract void radioOff(MobileRadioEvent paramMobileRadioEvent);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.mobile.MobileRadioListener
 * JD-Core Version:    0.5.4
 */