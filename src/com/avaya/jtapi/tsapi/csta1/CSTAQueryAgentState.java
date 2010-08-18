package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAQueryAgentState extends CSTARequest {
	public static CSTAQueryAgentState decode(final InputStream in) {
		final CSTAQueryAgentState _this = new CSTAQueryAgentState();
		_this.doDecode(in);

		return _this;
	}

	String device;

	public static final int PDU = 33;

	public CSTAQueryAgentState() {
	}

	public CSTAQueryAgentState(final String _device) {
		device = _device;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
	}

	@Override
	public int getPDU() {
		return 33;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueryAgentState ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));

		lines.add("}");
		return lines;
	}
}
