package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class ACSKeyRequest extends ACSRequest {
	public static ACSKeyRequest decode(final InputStream in) {
		final ACSKeyRequest _this = new ACSKeyRequest();
		_this.doDecode(in);

		return _this;
	}

	String loginID;

	public static final int PDU = 8;

	public ACSKeyRequest() {
	}

	public ACSKeyRequest(final String _loginID) {
		loginID = _loginID;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		loginID = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(loginID, memberStream);
	}

	public String getLoginID() {
		return loginID;
	}

	@Override
	public int getPDU() {
		return 8;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSKeyRequest ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(loginID, "loginID", indent));

		lines.add("}");
		return lines;
	}
}
