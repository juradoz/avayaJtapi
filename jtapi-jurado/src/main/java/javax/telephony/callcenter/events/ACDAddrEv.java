package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.AgentTerminal;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.events.AddrEv;

/** @deprecated */
public abstract interface ACDAddrEv extends CallCentEv, AddrEv {
	public abstract Agent getAgent();

	/** @deprecated */
	public abstract AgentTerminal getAgentTerminal();

	/** @deprecated */
	public abstract int getState();

	/** @deprecated */
	public abstract Address getAgentAddress();

	/** @deprecated */
	public abstract CallCenterTrunk[] getTrunks();
}