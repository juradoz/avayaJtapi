package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAReadyEvent extends CSTAUnsolicited {
	CSTAExtendedDeviceID agentDevice;
	String agentID;
	public static final int PDU = 75;

	public static CSTAReadyEvent decode(final InputStream in) {
		final CSTAReadyEvent _this = new CSTAReadyEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
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
		return 75;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAReadyEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAExtendedDeviceID.print(agentDevice, "agentDevice",
				indent));
		lines.addAll(ASNIA5String.print(agentID, "agentID", indent));

		lines.add("}");
		return lines;
	}

	public void setAgentDevice(final CSTAExtendedDeviceID agentDevice) {
		this.agentDevice = agentDevice;
	}

	public void setAgentID(final String agentID) {
		this.agentID = agentID;
	}
}
