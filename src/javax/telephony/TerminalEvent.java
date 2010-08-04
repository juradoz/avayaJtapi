package javax.telephony;

public abstract interface TerminalEvent extends Event
{
  public static final int TERMINAL_EVENT_TRANSMISSION_ENDED = 121;

  public abstract Terminal getTerminal();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.TerminalEvent
 * JD-Core Version:    0.5.4
 */