package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentQueryUcid extends LucentPrivateData {
	public static LucentQueryUcid decode(final InputStream in) {
		final LucentQueryUcid _this = new LucentQueryUcid();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID call;

	public static final int PDU = 76;

	public LucentQueryUcid() {
	}

	public LucentQueryUcid(final CSTAConnectionID _call) {
		call = _call;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		call = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(call, memberStream);
	}

	@Override
	public int getPDU() {
		return 76;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryUcid ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(call, "call", indent));

		lines.add("}");
		return lines;
	}
}
