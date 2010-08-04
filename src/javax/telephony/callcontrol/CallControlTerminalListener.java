package javax.telephony.callcontrol;

import javax.telephony.TerminalListener;

public abstract interface CallControlTerminalListener extends TerminalListener
{
  public abstract void terminalDoNotDisturb(CallControlTerminalEvent paramCallControlTerminalEvent);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.CallControlTerminalListener
 * JD-Core Version:    0.5.4
 */