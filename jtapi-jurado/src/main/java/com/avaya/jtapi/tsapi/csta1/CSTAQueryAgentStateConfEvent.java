package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAQueryAgentStateConfEvent extends CSTAConfirmation {
	short agentState;
	public static final int PDU = 34;

	public static CSTAQueryAgentStateConfEvent decode(InputStream in) {
		CSTAQueryAgentStateConfEvent _this = new CSTAQueryAgentStateConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.agentState = AgentState.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		AgentState.encode(this.agentState, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueryAgentStateConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(AgentState.print(this.agentState, "agentState", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 34;
	}

	public short getAgentState() {
		return this.agentState;
	}

	public void setAgentState(short agentState) {
		this.agentState = agentState;
	}
}