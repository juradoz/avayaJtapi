package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class LucentSetAgentStateConfEvent extends LucentPrivateData {
	boolean isPending;
	public static final int PDU = 103;

	public static LucentSetAgentStateConfEvent decode(final InputStream in) {
		final LucentSetAgentStateConfEvent _this = new LucentSetAgentStateConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		isPending = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNBoolean.encode(isPending, memberStream);
	}

	@Override
	public int getPDU() {
		return 103;
	}

	public boolean isPending() {
		return isPending;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSetAgentStateConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNBoolean.print(isPending, "isPending", indent));

		lines.add("}");
		return lines;
	}

	public void setPending(final boolean isPending) {
		this.isPending = isPending;
	}
}
