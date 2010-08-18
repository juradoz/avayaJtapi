package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class CSTAQueryAgentStateConfEvent extends CSTAConfirmation {
	short agentState;
	public static final int PDU = 34;

	public static CSTAQueryAgentStateConfEvent decode(final InputStream in) {
		final CSTAQueryAgentStateConfEvent _this = new CSTAQueryAgentStateConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		agentState = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(agentState, memberStream);
	}

	public short getAgentState() {
		return agentState;
	}

	@Override
	public int getPDU() {
		return 34;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueryAgentStateConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(AgentState.print(agentState, "agentState", indent));

		lines.add("}");
		return lines;
	}

	public void setAgentState(final short agentState) {
		this.agentState = agentState;
	}
}
