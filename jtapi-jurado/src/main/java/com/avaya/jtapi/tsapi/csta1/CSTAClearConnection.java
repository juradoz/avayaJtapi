package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAClearConnection extends CSTARequest {
	CSTAConnectionID call;
	public static final int PDU = 9;

	public CSTAClearConnection(CSTAConnectionID _call) {
		this.call = _call;
	}

	public CSTAClearConnection() {
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.call, memberStream);
	}

	public static CSTAClearConnection decode(InputStream in) {
		CSTAClearConnection _this = new CSTAClearConnection();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.call = CSTAConnectionID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAClearConnection ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.call, "call", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 9;
	}

	public CSTAConnectionID getCall() {
		return this.call;
	}
}