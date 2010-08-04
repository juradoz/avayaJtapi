package javax.telephony.mobile;

public abstract interface MobileNetwork
{
  public abstract boolean isAvailable();

  public abstract boolean isCurrent();

  public abstract boolean isRestricted();

  public abstract String[] getNames();

  public abstract String getCode();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.mobile.MobileNetwork
 * JD-Core Version:    0.5.4
 */