package javax.telephony.callcontrol.capabilities;

import javax.telephony.capabilities.TerminalConnectionCapabilities;

public abstract interface CallControlTerminalConnectionCapabilities extends TerminalConnectionCapabilities
{
  public abstract boolean canHold();

  public abstract boolean canUnhold();

  public abstract boolean canJoin();

  public abstract boolean canLeave();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.capabilities.CallControlTerminalConnectionCapabilities
 * JD-Core Version:    0.5.4
 */