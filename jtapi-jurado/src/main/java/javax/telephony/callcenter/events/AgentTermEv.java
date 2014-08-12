package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.Agent;
import javax.telephony.events.TermEv;

/** @deprecated */
public abstract interface AgentTermEv extends CallCentEv, TermEv {
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