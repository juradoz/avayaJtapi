package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAQueryFwdConfEvent extends CSTAConfirmation {
	CSTAForwardingInfo[] forward;
	public static final int PDU = 32;

	public static CSTAQueryFwdConfEvent decode(final InputStream in) {
		final CSTAQueryFwdConfEvent _this = new CSTAQueryFwdConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		forward = ListForwardParameters.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ListForwardParameters.encode(forward, memberStream);
	}

	public CSTAForwardingInfo[] getForward() {
		return forward;
	}

	@Override
	public int getPDU() {
		return 32;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueryFwdConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ListForwardParameters.print(forward, "forward", indent));

		lines.add("}");
		return lines;
	}

	public void setForward(final CSTAForwardingInfo[] forward) {
		this.forward = forward;
	}
}
