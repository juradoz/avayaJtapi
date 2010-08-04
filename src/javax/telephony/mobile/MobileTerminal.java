package javax.telephony.mobile;

import javax.telephony.MethodNotSupportedException;
import javax.telephony.Terminal;

public abstract interface MobileTerminal extends Terminal
{
  public abstract String getTerminalId();

  public abstract String getManufacturerName();

  public abstract String getSoftwareVersion();

  public abstract String getTypeApproval();

  public abstract boolean startDTMF(char paramChar)
    throws MethodNotSupportedException;

  public abstract void stopDTMF();

  public abstract boolean generateDTMF(String paramString)
    throws MethodNotSupportedException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.mobile.MobileTerminal
 * JD-Core Version:    0.5.4
 */