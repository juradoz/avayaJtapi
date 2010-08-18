package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class CSTAQueryDndConfEvent extends CSTAConfirmation {
	boolean doNotDisturb;
	public static final int PDU = 30;

	public static CSTAQueryDndConfEvent decode(final InputStream in) {
		final CSTAQueryDndConfEvent _this = new CSTAQueryDndConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		doNotDisturb = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNBoolean.encode(doNotDisturb, memberStream);
	}

	@Override
	public int getPDU() {
		return 30;
	}

	public boolean isDoNotDisturb() {
		return doNotDisturb;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueryDndConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNBoolean.print(doNotDisturb, "doNotDisturb", indent));

		lines.add("}");
		return lines;
	}

	public void setDoNotDisturb(final boolean doNotDisturb) {
		this.doNotDisturb = doNotDisturb;
	}
}
