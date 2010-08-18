package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.AgentTerminal;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.events.AddrEv;

/** @deprecated */
@Deprecated
public abstract interface ACDAddrEv extends CallCentEv, AddrEv {
	public abstract Agent getAgent();

	/** @deprecated */
	@Deprecated
	public abstract Address getAgentAddress();

	/** @deprecated */
	@Deprecated
	public abstract AgentTerminal getAgentTerminal();

	/** @deprecated */
	@Deprecated
	public abstract int getState();

	/** @deprecated */
	@Deprecated
	public abstract CallCenterTrunk[] getTrunks();
}
