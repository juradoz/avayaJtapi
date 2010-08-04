package javax.telephony.callcenter;

import javax.telephony.Address;
import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;

public abstract interface Agent
{
  public static final int UNKNOWN = 0;
  public static final int LOG_IN = 1;
  public static final int LOG_OUT = 2;
  public static final int NOT_READY = 3;
  public static final int READY = 4;
  public static final int WORK_NOT_READY = 5;
  public static final int WORK_READY = 6;
  public static final int BUSY = 7;

  public abstract void setState(int paramInt)
    throws InvalidArgumentException, InvalidStateException;

  public abstract int getState();

  public abstract String getAgentID();

  public abstract ACDAddress getACDAddress();

  public abstract Address getAgentAddress();

  public abstract AgentTerminal getAgentTerminal();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.Agent
 * JD-Core Version:    0.5.4
 */