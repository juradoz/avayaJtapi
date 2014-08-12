package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAQueryDndConfEvent extends CSTAConfirmation {
	boolean doNotDisturb;
	public static final int PDU = 30;

	public static CSTAQueryDndConfEvent decode(InputStream in) {
		CSTAQueryDndConfEvent _this = new CSTAQueryDndConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.doNotDisturb = ASNBoolean.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		ASNBoolean.encode(this.doNotDisturb, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAQueryDndConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean
				.print(this.doNotDisturb, "doNotDisturb", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 30;
	}

	public boolean isDoNotDisturb() {
		return this.doNotDisturb;
	}

	public void setDoNotDisturb(boolean doNotDisturb) {
		this.doNotDisturb = doNotDisturb;
	}
}