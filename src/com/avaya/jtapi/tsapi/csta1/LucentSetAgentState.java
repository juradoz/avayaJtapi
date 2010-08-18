package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LucentSetAgentState extends LucentPrivateData {
	public static LucentSetAgentState decode(final InputStream in,
			final CSTATSProvider prov) {
		final LucentSetAgentState _this = new LucentSetAgentState();
		_this.doDecode(in);

		return _this;
	}

	short workMode;

	static final int PDU = 10;

	public LucentSetAgentState() {
	}

	public LucentSetAgentState(final short _workMode) {
		workMode = _workMode;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		workMode = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(workMode, memberStream);
	}

	@Override
	public int getPDU() {
		return 10;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSetAgentState ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentWorkMode.print(workMode, "workMode", indent));

		lines.add("}");
		return lines;
	}
}
