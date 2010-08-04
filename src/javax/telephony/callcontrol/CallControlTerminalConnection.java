package javax.telephony.callcontrol;

import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PrivilegeViolationException;
import javax.telephony.ResourceUnavailableException;
import javax.telephony.TerminalConnection;

public abstract interface CallControlTerminalConnection extends TerminalConnection
{
  public static final int IDLE = 96;
  public static final int RINGING = 97;
  public static final int TALKING = 98;
  public static final int HELD = 99;
  public static final int BRIDGED = 100;
  public static final int INUSE = 101;
  public static final int DROPPED = 102;
  public static final int UNKNOWN = 103;

  public abstract int getCallControlState();

  public abstract void hold()
    throws InvalidStateException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;

  public abstract void unhold()
    throws InvalidStateException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;

  public abstract void join()
    throws InvalidStateException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;

  public abstract void leave()
    throws InvalidStateException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.CallControlTerminalConnection
 * JD-Core Version:    0.5.4
 */