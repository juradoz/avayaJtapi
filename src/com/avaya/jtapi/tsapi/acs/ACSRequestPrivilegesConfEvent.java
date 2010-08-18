package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class ACSRequestPrivilegesConfEvent extends ACSConfirmation {
	String nonce;
	public static final int PDU = 18;

	public static ACSRequestPrivilegesConfEvent decode(final InputStream in) {
		final ACSRequestPrivilegesConfEvent _this = new ACSRequestPrivilegesConfEvent();

		_this.doDecode(in);

		return _this;
	}

	public ACSRequestPrivilegesConfEvent() {
	}

	public ACSRequestPrivilegesConfEvent(final String nonce) {
		this.nonce = nonce;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		nonce = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(nonce, memberStream);
	}

	public synchronized String get_nonce() {
		return nonce;
	}

	@Override
	public int getPDU() {
		return 18;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSRequestPrivilegesConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(nonce, "nonce", indent));

		lines.add("}");
		return lines;
	}
}
