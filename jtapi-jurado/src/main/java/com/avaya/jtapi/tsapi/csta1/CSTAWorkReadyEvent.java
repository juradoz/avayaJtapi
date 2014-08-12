package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAWorkReadyEvent extends CSTAUnsolicited {
	CSTAExtendedDeviceID agentDevice;
	String agentID;
	public static final int PDU = 77;

	public static CSTAWorkReadyEvent decode(InputStream in) {
		CSTAWorkReadyEvent _this = new CSTAWorkReadyEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.agentDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.agentID = AgentID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAWorkReadyEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(CSTAExtendedDeviceID.print(this.agentDevice,
				"agentDevice", indent));
		lines.addAll(AgentID.print(this.agentID, "agentID", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 77;
	}

	public CSTAExtendedDeviceID getAgentDevice() {
		return this.agentDevice;
	}

	public String getAgentID() {
		return this.agentID;
	}

	public void setAgentDevice(CSTAExtendedDeviceID agentDevice) {
		this.agentDevice = agentDevice;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
}