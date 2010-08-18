package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentV6SetAgentState extends LucentV5SetAgentState {
	public static LucentSetAgentState decode(final InputStream in,
			final CSTATSProvider prov) {
		final LucentV6SetAgentState _this = new LucentV6SetAgentState();
		_this.doDecode(in);

		return _this;
	}

	boolean enablePending;

	public static final int PDU = 102;

	public LucentV6SetAgentState() {
	}

	public LucentV6SetAgentState(final short _workMode, final int _reasonCode,
			final boolean _enablePending) {
		super(_workMode, _reasonCode);
		enablePending = _enablePending;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		enablePending = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNBoolean.encode(enablePending, memberStream);
	}

	@Override
	public int getPDU() {
		return 102;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6SetAgentState ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentWorkMode.print(workMode, "workMode", indent));
		lines.addAll(ASNInteger.print(reasonCode, "reasonCode", indent));
		lines.addAll(ASNBoolean.print(enablePending, "enablePending", indent));

		lines.add("}");
		return lines;
	}
}
