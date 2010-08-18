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

	public static CSTASetAgentState decode(final InputStream in) {
		final CSTASetAgentState _this = new CSTASetAgentState();
		_this.doDecode(in);

		return _this;
	}

	public CSTASetAgentState() {
	}

	public CSTASetAgentState(final String _device, final short _agentMode,
			final String _agentID, final String _agentGroup,
			final String _agentPassword) {
		device = _device;
		agentMode = _agentMode;
		agentID = _agentID;
		agentGroup = _agentGroup;
		agentPassword = _agentPassword;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
		agentMode = ASNEnumerated.decode(memberStream);
		agentID = ASNIA5String.decode(memberStream);
		agentGroup = ASNIA5String.decode(memberStream);
		agentPassword = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASetAgentState ::=");
		lines.add("{");

		final String indent = "  ";

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
