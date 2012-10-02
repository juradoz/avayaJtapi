package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSRequestPrivilegesConfEvent extends ACSConfirmation {
	String nonce;
	public static final int PDU = 18;

	public ACSRequestPrivilegesConfEvent() {
	}

	public ACSRequestPrivilegesConfEvent(String nonce) {
		this.nonce = nonce;
	}

	public static ACSRequestPrivilegesConfEvent decode(InputStream in) {
		ACSRequestPrivilegesConfEvent _this = new ACSRequestPrivilegesConfEvent();

		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.nonce = ASNIA5String.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(this.nonce, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSRequestPrivilegesConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(this.nonce, "nonce", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 18;
	}

	public String get_nonce() {
		return this.nonce;
	}
}