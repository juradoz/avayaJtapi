package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAAnswerCall extends CSTARequest {
	public static CSTAAnswerCall decode(final InputStream in) {
		final CSTAAnswerCall _this = new CSTAAnswerCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID alertingCall;

	public static final int PDU = 3;

	public CSTAAnswerCall() {
	}

	public CSTAAnswerCall(final CSTAConnectionID _alertingCall) {
		alertingCall = _alertingCall;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		alertingCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(alertingCall, memberStream);
	}

	public CSTAConnectionID getAlertingCall() {
		return alertingCall;
	}

	@Override
	public int getPDU() {
		return 3;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAAnswerCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(alertingCall, "alertingCall",
				indent));

		lines.add("}");
		return lines;
	}
}
