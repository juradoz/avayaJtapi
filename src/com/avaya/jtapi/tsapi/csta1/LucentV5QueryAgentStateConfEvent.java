package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentV5QueryAgentStateConfEvent extends
		LucentQueryAgentStateConfEvent {
	int reasonCode;
	static final int PDU = 88;

	public static LucentQueryAgentStateConfEvent decode(InputStream in,
			CSTATSProvider prov) {
		LucentV5QueryAgentStateConfEvent _this = new LucentV5QueryAgentStateConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		reasonCode = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNInteger.encode(reasonCode, memberStream);
	}

	@Override
	public int getPDU() {
		return 88;
	}

	public int getReasonCode() {
		return reasonCode;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5QueryAgentStateConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(workMode, "workMode", indent));
		lines.addAll(LucentTalkState.print(talkState, "talkState", indent));
		lines.addAll(ASNInteger.print(reasonCode, "reasonCode", indent));

		lines.add("}");
		return lines;
	}

	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}
}
