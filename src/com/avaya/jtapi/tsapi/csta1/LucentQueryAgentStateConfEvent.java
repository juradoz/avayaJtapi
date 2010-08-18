package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LucentQueryAgentStateConfEvent extends LucentPrivateData {
	short workMode;
	short talkState;
	static final int PDU = 17;

	static LucentQueryAgentStateConfEvent decode(InputStream in) {
		LucentQueryAgentStateConfEvent _this = new LucentQueryAgentStateConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		workMode = ASNEnumerated.decode(memberStream);
		talkState = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(workMode, memberStream);
		ASNEnumerated.encode(talkState, memberStream);
	}

	@Override
	public int getPDU() {
		return 17;
	}

	public short getTalkState() {
		return talkState;
	}

	public short getWorkMode() {
		return workMode;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentStateConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(workMode, "workMode", indent));
		lines.addAll(LucentTalkState.print(talkState, "talkState", indent));

		lines.add("}");
		return lines;
	}

	public void setTalkState(short talkState) {
		this.talkState = talkState;
	}

	public void setWorkMode(short workMode) {
		this.workMode = workMode;
	}
}
