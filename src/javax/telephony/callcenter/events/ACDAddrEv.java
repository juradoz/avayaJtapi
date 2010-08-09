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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcenter.events.ACDAddrEv JD-Core Version: 0.5.4
 */