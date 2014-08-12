package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAAnswerCall extends CSTARequest {
	CSTAConnectionID alertingCall;
	public static final int PDU = 3;

	public CSTAConnectionID getAlertingCall() {
		return this.alertingCall;
	}

	public CSTAAnswerCall(CSTAConnectionID _alertingCall) {
		this.alertingCall = _alertingCall;
	}

	public CSTAAnswerCall() {
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.alertingCall, memberStream);
	}

	public static CSTAAnswerCall decode(InputStream in) {
		CSTAAnswerCall _this = new CSTAAnswerCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.alertingCall = CSTAConnectionID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAAnswerCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.alertingCall, "alertingCall",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 3;
	}
}