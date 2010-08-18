package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAClearConnection extends CSTARequest {
	public static CSTAClearConnection decode(InputStream in) {
		CSTAClearConnection _this = new CSTAClearConnection();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID call;

	public static final int PDU = 9;

	public CSTAClearConnection() {
	}

	public CSTAClearConnection(CSTAConnectionID _call) {
		call = _call;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		call = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(call, memberStream);
	}

	public CSTAConnectionID getCall() {
		return call;
	}

	@Override
	public int getPDU() {
		return 9;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAClearConnection ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(call, "call", indent));

		lines.add("}");
		return lines;
	}
}

