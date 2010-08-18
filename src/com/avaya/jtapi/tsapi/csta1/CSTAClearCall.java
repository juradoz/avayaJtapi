package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAClearCall extends CSTARequest {
	public static CSTAClearCall decode(final InputStream in) {
		final CSTAClearCall _this = new CSTAClearCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID call;

	public static final int PDU = 7;

	public CSTAClearCall() {
	}

	public CSTAClearCall(final CSTAConnectionID _call) {
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

	public CSTAConnectionID getCall() {
		return call;
	}

	@Override
	public int getPDU() {
		return 7;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAClearCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(call, "call", indent));

		lines.add("}");
		return lines;
	}
}
