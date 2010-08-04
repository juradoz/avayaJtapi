package javax.telephony.callcenter;

import javax.telephony.Address;
import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.ResourceUnavailableException;
import javax.telephony.Terminal;

public abstract interface AgentTerminal extends Terminal
{
  public abstract Agent addAgent(Address paramAddress, ACDAddress paramACDAddress, int paramInt, String paramString1, String paramString2)
    throws InvalidArgumentException, InvalidStateException, ResourceUnavailableException;

  public abstract void removeAgent(Agent paramAgent)
    throws InvalidArgumentException, InvalidStateException;

  public abstract Agent[] getAgents();

  /** @deprecated */
  public abstract void setAgents(Agent[] paramArrayOfAgent)
    throws MethodNotSupportedException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.AgentTerminal
 * JD-Core Version:    0.5.4
 */