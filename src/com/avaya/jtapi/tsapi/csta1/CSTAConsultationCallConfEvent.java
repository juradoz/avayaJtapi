package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAConsultationCallConfEvent extends CSTAConfirmation {
	public static CSTAConsultationCallConfEvent decode(final InputStream in) {
		final CSTAConsultationCallConfEvent _this = new CSTAConsultationCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID newCall;

	public static final int PDU = 14;

	public CSTAConsultationCallConfEvent() {
	}

	public CSTAConsultationCallConfEvent(final CSTAConnectionID _newCall) {
		newCall = _newCall;
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
		return 14;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAConsultationCallConfEvent ::=");
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
