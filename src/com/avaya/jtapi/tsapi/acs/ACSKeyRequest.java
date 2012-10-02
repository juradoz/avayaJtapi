package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSKeyRequest extends ACSRequest {
	String loginID;
	public static final int PDU = 8;

	public ACSKeyRequest(String _loginID) {
		this.loginID = _loginID;
	}

	public ACSKeyRequest() {
	}

	public void encodeMembers(OutputStream memberStream) {
		LoginID.encode(this.loginID, memberStream);
	}

	public static ACSKeyRequest decode(InputStream in) {
		ACSKeyRequest _this = new ACSKeyRequest();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.loginID = LoginID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSKeyRequest ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LoginID.print(this.loginID, "loginID", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 8;
	}

	public String getLoginID() {
		return this.loginID;
	}
}