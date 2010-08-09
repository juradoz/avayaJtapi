package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAAnswerCall extends CSTARequest {
	public static CSTAAnswerCall decode(InputStream in) {
		CSTAAnswerCall _this = new CSTAAnswerCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID alertingCall;

	public static final int PDU = 3;

	public CSTAAnswerCall() {
	}

	public CSTAAnswerCall(CSTAConnectionID _alertingCall) {
		alertingCall = _alertingCall;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		alertingCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();
		lines.add("CSTAAnswerCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(alertingCall, "alertingCall",
				indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAAnswerCall JD-Core Version: 0.5.4
 */