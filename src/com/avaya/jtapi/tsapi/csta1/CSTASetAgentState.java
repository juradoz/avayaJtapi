package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTASetAgentState extends CSTARequest {
	String device;
	short agentMode;
	String agentID;
	String agentGroup;
	String agentPassword;
	public static final int PDU = 49;

	public static CSTASetAgentState decode(InputStream in) {
		CSTASetAgentState _this = new CSTASetAgentState();
		_this.doDecode(in);

		return _this;
	}

	public CSTASetAgentState() {
	}

	public CSTASetAgentState(String _device, short _agentMode, String _agentID,
			String _agentGroup, String _agentPassword) {
		device = _device;
		agentMode = _agentMode;
		agentID = _agentID;
		agentGroup = _agentGroup;
		agentPassword = _agentPassword;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
		agentMode = ASNEnumerated.decode(memberStream);
		agentID = ASNIA5String.decode(memberStream);
		agentGroup = ASNIA5String.decode(memberStream);
		agentPassword = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
		ASNEnumerated.encode(agentMode, memberStream);
		ASNIA5String.encode(agentID, memberStream);
		ASNIA5String.encode(agentGroup, memberStream);
		ASNIA5String.encode(agentPassword, memberStream);
	}

	public String getAgentGroup() {
		return agentGroup;
	}

	public String getAgentID() {
		return agentID;
	}

	public short getAgentMode() {
		return agentMode;
	}

	public String getAgentPassword() {
		return agentPassword;
	}

	public String getDevice() {
		return device;
	}

	@Override
	public int getPDU() {
		return 49;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASetAgentState ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));
		lines.addAll(AgentMode.print(agentMode, "agentMode", indent));
		lines.addAll(ASNIA5String.print(agentID, "agentID", indent));
		lines.addAll(ASNIA5String.print(agentGroup, "agentGroup", indent));
		lines
				.addAll(ASNIA5String.print(agentPassword, "agentPassword",
						indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASetAgentState JD-Core Version: 0.5.4
 */