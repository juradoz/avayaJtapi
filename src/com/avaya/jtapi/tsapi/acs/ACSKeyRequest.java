package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class ACSKeyRequest extends ACSRequest {
	public static ACSKeyRequest decode(InputStream in) {
		ACSKeyRequest _this = new ACSKeyRequest();
		_this.doDecode(in);

		return _this;
	}

	String loginID;

	public static final int PDU = 8;

	public ACSKeyRequest() {
	}

	public ACSKeyRequest(String _loginID) {
		loginID = _loginID;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		loginID = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();
		lines.add("ACSKeyRequest ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(loginID, "loginID", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSKeyRequest JD-Core Version: 0.5.4
 */