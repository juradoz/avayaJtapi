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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcenter.events.AgentTermEv JD-Core Version: 0.5.4
 */