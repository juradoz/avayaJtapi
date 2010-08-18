package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAWorkNotReadyEvent extends CSTAUnsolicited {
	CSTAExtendedDeviceID agentDevice;
	String agentID;
	public static final int PDU = 76;

	public static CSTAWorkNotReadyEvent decode(InputStream in) {
		CSTAWorkNotReadyEvent _this = new CSTAWorkNotReadyEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		agentDevice = CSTAExtendedDeviceID.decode(memberStream);
		agentID = ASNIA5String.decode(memberStream);
	}

	public CSTAExtendedDeviceID getAgentDevice() {
		return agentDevice;
	}

	public String getAgentID() {
		return agentID;
	}

	@Override
	public int getPDU() {
		return 76;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAWorkNotReadyEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAExtendedDeviceID.print(agentDevice, "agentDevice",
				indent));
		lines.addAll(ASNIA5String.print(agentID, "agentID", indent));

		lines.add("}");
		return lines;
	}

	public void setAgentDevice(CSTAExtendedDeviceID agentDevice) {
		this.agentDevice = agentDevice;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
}

