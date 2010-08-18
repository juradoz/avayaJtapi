package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentV5SetAgentState extends LucentSetAgentState {
	public static LucentSetAgentState decode(final InputStream in,
			final CSTATSProvider prov) {
		final LucentV5SetAgentState _this = new LucentV5SetAgentState();
		_this.doDecode(in);

		return _this;
	}

	int reasonCode;

	static final int PDU = 87;

	public LucentV5SetAgentState() {
	}

	public LucentV5SetAgentState(final short _workMode, final int _reasonCode) {
		super(_workMode);
		reasonCode = _reasonCode;
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
		return 87;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5SetAgentState ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentWorkMode.print(workMode, "workMode", indent));
		lines.addAll(ASNInteger.print(reasonCode, "reasonCode", indent));

		lines.add("}");
		return lines;
	}
}
