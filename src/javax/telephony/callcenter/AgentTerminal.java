package javax.telephony.callcenter;

import javax.telephony.Address;
import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.ResourceUnavailableException;
import javax.telephony.Terminal;

public abstract interface AgentTerminal extends Terminal {
	public abstract Agent addAgent(Address paramAddress,
			ACDAddress paramACDAddress, int paramInt, String paramString1,
			String paramString2) throws InvalidArgumentException,
			InvalidStateException, ResourceUnavailableException;

	public abstract Agent[] getAgents();

	public abstract void removeAgent(Agent paramAgent)
			throws InvalidArgumentException, InvalidStateException;

	/** @deprecated */
	@Deprecated
	public abstract void setAgents(Agent[] paramArrayOfAgent)
			throws MethodNotSupportedException;
}

