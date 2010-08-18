package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAAlternateCall extends CSTARequest {
	public static CSTAAlternateCall decode(InputStream in) {
		CSTAAlternateCall _this = new CSTAAlternateCall();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID activeCall;
	CSTAConnectionID otherCall;

	public static final int PDU = 1;

	public CSTAAlternateCall() {
	}

	public CSTAAlternateCall(CSTAConnectionID _activeCall,
			CSTAConnectionID _otherCall) {
		activeCall = _activeCall;
		otherCall = _otherCall;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		activeCall = CSTAConnectionID.decode(memberStream);
		otherCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(activeCall, memberStream);
		CSTAConnectionID.encode(otherCall, memberStream);
	}

	public CSTAConnectionID getActiveCall() {
		return activeCall;
	}

	public CSTAConnectionID getOtherCall() {
		return otherCall;
	}

	@Override
	public int getPDU() {
		return 1;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAAlternateCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));
		lines.addAll(CSTAConnectionID.print(otherCall, "otherCall", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAAlternateCall JD-Core Version: 0.5.4
 */