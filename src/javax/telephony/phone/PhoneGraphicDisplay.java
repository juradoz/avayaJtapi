package javax.telephony.phone;

import java.awt.Dimension;
import java.awt.Graphics;

public abstract interface PhoneGraphicDisplay extends Component
{
  public abstract Graphics getGraphics();

  public abstract Dimension size();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.phone.PhoneGraphicDisplay
 * JD-Core Version:    0.5.4
 */