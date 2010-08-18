package javax.telephony.callcenter;

import javax.telephony.TerminalListener;

public abstract interface AgentTerminalListener extends TerminalListener {
	public abstract void agentTerminalBusy(
			AgentTerminalEvent paramAgentTerminalEvent);

	public abstract void agentTerminalLoggedOff(
			AgentTerminalEvent paramAgentTerminalEvent);

	public abstract void agentTerminalLoggedOn(
			AgentTerminalEvent paramAgentTerminalEvent);

	public abstract void agentTerminalNotReady(
			AgentTerminalEvent paramAgentTerminalEvent);

	public abstract void agentTerminalReady(
			AgentTerminalEvent paramAgentTerminalEvent);

	public abstract void agentTerminalUnknown(
			AgentTerminalEvent paramAgentTerminalEvent);

	public abstract void agentTerminalWorkNotReady(
			AgentTerminalEvent paramAgentTerminalEvent);

	public abstract void agentTerminalWorkReady(
			AgentTerminalEvent paramAgentTerminalEvent);
}
