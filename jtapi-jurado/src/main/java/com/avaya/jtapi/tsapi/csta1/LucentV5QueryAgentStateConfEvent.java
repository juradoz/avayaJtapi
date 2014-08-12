package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

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

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.reasonCode = ASNInteger.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNInteger.encode(this.reasonCode, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5QueryAgentStateConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
		lines.addAll(LucentTalkState.print(this.talkState, "talkState", indent));
		lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 88;
	}

	public int getReasonCode() {
		return this.reasonCode;
	}

	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}
}