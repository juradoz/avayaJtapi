package javax.telephony.callcontrol;

import javax.telephony.TerminalEvent;

public abstract interface CallControlTerminalEvent extends CallControlEvent, TerminalEvent
{
  public static final int CALLCTL_TERMINAL_EVENT_DO_NOT_DISTURB = 371;

  public abstract boolean getDoNotDisturbState();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.CallControlTerminalEvent
 * JD-Core Version:    0.5.4
 */