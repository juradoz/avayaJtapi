package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAMakePredictiveCallConfEvent extends CSTAConfirmation {
	CSTAConnectionID newCall;
	public static final int PDU = 26;

	public static CSTAMakePredictiveCallConfEvent decode(InputStream in) {
		CSTAMakePredictiveCallConfEvent _this = new CSTAMakePredictiveCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		newCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAMakePredictiveCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(newCall, "newCall", indent));

		lines.add("}");
		return lines;
	}

	public void setNewCall(CSTAConnectionID newCall) {
		this.newCall = newCall;
	}
}
