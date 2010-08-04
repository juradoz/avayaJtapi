package javax.telephony.callcenter;

import javax.telephony.TerminalListener;

public abstract interface AgentTerminalListener extends TerminalListener
{
  public abstract void agentTerminalBusy(AgentTerminalEvent paramAgentTerminalEvent);

  public abstract void agentTerminalLoggedOff(AgentTerminalEvent paramAgentTerminalEvent);

  public abstract void agentTerminalLoggedOn(AgentTerminalEvent paramAgentTerminalEvent);

  public abstract void agentTerminalNotReady(AgentTerminalEvent paramAgentTerminalEvent);

  public abstract void agentTerminalReady(AgentTerminalEvent paramAgentTerminalEvent);

  public abstract void agentTerminalUnknown(AgentTerminalEvent paramAgentTerminalEvent);

  public abstract void agentTerminalWorkNotReady(AgentTerminalEvent paramAgentTerminalEvent);

  public abstract void agentTerminalWorkReady(AgentTerminalEvent paramAgentTerminalEvent);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.AgentTerminalListener
 * JD-Core Version:    0.5.4
 */