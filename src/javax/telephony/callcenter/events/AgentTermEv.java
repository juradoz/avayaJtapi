package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.Agent;
import javax.telephony.events.TermEv;

/** @deprecated */
@Deprecated
public abstract interface AgentTermEv extends CallCentEv, TermEv {
	/** @deprecated */
	@Deprecated
	public abstract ACDAddress getACDAddress();

	public abstract Agent getAgent();

	/** @deprecated */
	@Deprecated
	public abstract Address getAgentAddress();

	/** @deprecated */
	@Deprecated
	public abstract String getAgentID();

	/** @deprecated */
	@Deprecated
	public abstract int getState();
}
