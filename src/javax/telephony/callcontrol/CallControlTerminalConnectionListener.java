package javax.telephony.callcontrol;

import javax.telephony.TerminalConnectionListener;

public abstract interface CallControlTerminalConnectionListener extends CallControlConnectionListener, TerminalConnectionListener
{
  public abstract void terminalConnectionBridged(CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

  public abstract void terminalConnectionDropped(CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

  public abstract void terminalConnectionHeld(CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

  public abstract void terminalConnectionInUse(CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

  public abstract void terminalConnectionRinging(CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

  public abstract void terminalConnectionTalking(CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);

  public abstract void terminalConnectionUnknown(CallControlTerminalConnectionEvent paramCallControlTerminalConnectionEvent);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.CallControlTerminalConnectionListener
 * JD-Core Version:    0.5.4
 */