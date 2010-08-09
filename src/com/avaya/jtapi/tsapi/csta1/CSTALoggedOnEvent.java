package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTALoggedOnEvent extends CSTAUnsolicited {
	CSTAExtendedDeviceID agentDevice;
	String agentID;
	String agentGroup;
	String password;
	public static final int PDU = 72;

	public static CSTALoggedOnEvent decode(InputStream in) {
		CSTALoggedOnEvent _this = new CSTALoggedOnEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		agentDevice = CSTAExtendedDeviceID.decode(memberStream);
		agentID = ASNIA5String.decode(memberStream);
		agentGroup = ASNIA5String.decode(memberStream);
		password = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNSequence.encode(agentDevice, memberStream);
		ASNIA5String.encode(agentID, memberStream);
		ASNIA5String.encode(agentGroup, memberStream);
		ASNIA5String.encode(password, memberStream);
	}

	public CSTAExtendedDeviceID getAgentDevice() {
		return agentDevice;
	}

	public String getAgentGroup() {
		return agentGroup;
	}

	public String getAgentID() {
		return agentID;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public int getPDU() {
		return 72;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("CSTALoggedOnEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAExtendedDeviceID.print(agentDevice, "agentDevice",
				indent));
		lines.addAll(ASNIA5String.print(agentID, "agentID", indent));
		lines.addAll(ASNIA5String.print(agentGroup, "agentGroup", indent));
		lines.addAll(ASNIA5String.print(password, "password", indent));

		lines.add("}");
		return lines;
	}

	public void setAgentDevice(CSTAExtendedDeviceID agentDevice) {
		this.agentDevice = agentDevice;
	}

	public void setAgentGroup(String agentGroup) {
		this.agentGroup = agentGroup;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTALoggedOnEvent JD-Core Version: 0.5.4
 */