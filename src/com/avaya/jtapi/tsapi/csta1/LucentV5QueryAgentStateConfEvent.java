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

	public static LucentQueryAgentStateConfEvent decode(final InputStream in,
			final CSTATSProvider prov) {
		final LucentV5QueryAgentStateConfEvent _this = new LucentV5QueryAgentStateConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		reasonCode = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5QueryAgentStateConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentWorkMode.print(workMode, "workMode", indent));
		lines.addAll(LucentTalkState.print(talkState, "talkState", indent));
		lines.addAll(ASNInteger.print(reasonCode, "reasonCode", indent));

		lines.add("}");
		return lines;
	}

	public void setReasonCode(final int reasonCode) {
		this.reasonCode = reasonCode;
	}
}
