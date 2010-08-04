package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.Agent;
import javax.telephony.events.TermEv;

/** @deprecated */
public abstract interface AgentTermEv extends CallCentEv, TermEv
{
  public abstract Agent getAgent();

  /** @deprecated */
  public abstract ACDAddress getACDAddress();

  /** @deprecated */
  public abstract String getAgentID();

  /** @deprecated */
  public abstract int getState();

  /** @deprecated */
  public abstract Address getAgentAddress();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.events.AgentTermEv
 * JD-Core Version:    0.5.4
 */