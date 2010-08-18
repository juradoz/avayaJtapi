package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAMakePredictiveCallConfEvent extends CSTAConfirmation {
	CSTAConnectionID newCall;
	public static final int PDU = 26;

	public static CSTAMakePredictiveCallConfEvent decode(final InputStream in) {
		final CSTAMakePredictiveCallConfEvent _this = new CSTAMakePredictiveCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		newCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(newCall, memberStream);
	}

	public CSTAConnectionID getNewCall() {
		return newCall;
	}

	@Override
	public int getPDU() {
		return 26;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAMakePredictiveCallConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(newCall, "newCall", indent));

		lines.add("}");
		return lines;
	}

	public void setNewCall(final CSTAConnectionID newCall) {
		this.newCall = newCall;
	}
}
