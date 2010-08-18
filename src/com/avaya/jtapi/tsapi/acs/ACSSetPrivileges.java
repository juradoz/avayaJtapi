package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class ACSSetPrivileges extends ACSRequest {
	public static ACSSetPrivileges decode(final InputStream in) {
		final ACSSetPrivileges _this = new ACSSetPrivileges();
		_this.doDecode(in);

		return _this;
	}

	String xmlData;

	public static final int PDU = 19;

	public ACSSetPrivileges() {
	}

	public ACSSetPrivileges(final String _xmlData) {
		xmlData = _xmlData;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		xmlData = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(xmlData, memberStream);
	}

	@Override
	public int getPDU() {
		return 19;
	}

	public String getxmlData() {
		return xmlData;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSSetPrivileges ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(xmlData, "xmlData", indent));

		lines.add("}");
		return lines;
	}
}
