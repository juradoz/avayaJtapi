package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTANotReadyEvent extends CSTAUnsolicited {
	CSTAExtendedDeviceID agentDevice;
	String agentID;
	public static final int PDU = 74;

	public static CSTANotReadyEvent decode(InputStream in) {
		CSTANotReadyEvent _this = new CSTANotReadyEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.agentDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.agentID = AgentID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAExtendedDeviceID.encode(this.agentDevice, memberStream);
		AgentID.encode(this.agentID, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTANotReadyEvent ::=");
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
		return 74;
	}

	public CSTAExtendedDeviceID getAgentDevice() {
		return this.agentDevice;
	}

	public void setAgentDevice(CSTAExtendedDeviceID agentDevice) {
		this.agentDevice = agentDevice;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

	public String getAgentID() {
		return this.agentID;
	}
}