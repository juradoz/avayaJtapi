package javax.telephony.callcenter.capabilities;

import javax.telephony.capabilities.TerminalCapabilities;

public abstract interface AgentTerminalCapabilities extends
		TerminalCapabilities {
	public abstract boolean canHandleAgents();
}