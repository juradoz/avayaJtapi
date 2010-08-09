package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class CSTAQueryDndConfEvent extends CSTAConfirmation {
	boolean doNotDisturb;
	public static final int PDU = 30;

	public static CSTAQueryDndConfEvent decode(InputStream in) {
		CSTAQueryDndConfEvent _this = new CSTAQueryDndConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		doNotDisturb = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("CSTAQueryDndConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean.print(doNotDisturb, "doNotDisturb", indent));

		lines.add("}");
		return lines;
	}

	public void setDoNotDisturb(boolean doNotDisturb) {
		this.doNotDisturb = doNotDisturb;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAQueryDndConfEvent JD-Core Version: 0.5.4
 */